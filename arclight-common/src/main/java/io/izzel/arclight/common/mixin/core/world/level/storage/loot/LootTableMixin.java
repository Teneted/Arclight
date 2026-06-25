package io.izzel.arclight.common.mixin.core.world.level.storage.loot;

import com.llamalad7.mixinextras.sugar.Local;
import io.izzel.arclight.common.bridge.core.world.level.storage.loot.LootTableBridge;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.bukkit.craftbukkit.CraftLootTable;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.world.LootGenerateEvent;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(LootTable.class)
public abstract class LootTableMixin implements LootTableBridge {

    @Shadow
    protected abstract ObjectArrayList<ItemStack> getRandomItems(LootContext context);

    @Shadow
    protected abstract List<Integer> getAvailableSlots(Container container, RandomSource random);

    @Shadow
    protected abstract void shuffleAndSplitItems(ObjectArrayList<ItemStack> result, int availableSlots, RandomSource random);

    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    @Final
    private Optional<Identifier> randomSequence;
    public CraftLootTable craftLootTable; // CraftBukkit

    @Inject(method = "fill", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getAvailableSlots(Lnet/minecraft/world/Container;Lnet/minecraft/util/RandomSource;)Ljava/util/List;"))
    private void arclight$callLootGenerateEvent(Container container, LootParams params, long optionalRandomSeed, CallbackInfo ci, @Local ObjectArrayList<ItemStack> itemStacks, @Local LootContext context) {
        // CraftBukkit start
        LootGenerateEvent event = CraftEventFactory.callLootGenerateEvent(container, ((LootTable) (Object) this), context, itemStacks, false);
        if (event.isCancelled()) {
            return;
        }
        itemStacks = event.getLoot().stream().map(CraftItemStack::asNMSCopy).collect(ObjectArrayList.toList());
        // CraftBukkit end
    }

    @Override
    public void fillInventory(Container container, LootParams params, long optionalRandomSeed, boolean plugin) {
        LootContext context = (new LootContext.Builder(params)).withOptionalRandomSeed(optionalRandomSeed).create(this.randomSequence);
        ObjectArrayList<ItemStack> itemStacks = this.getRandomItems(context);
        RandomSource random = context.getRandom();
        // CraftBukkit start
        LootGenerateEvent event = CraftEventFactory.callLootGenerateEvent(container, ((LootTable) (Object) this), context, itemStacks, plugin);
        if (event.isCancelled()) {
            return;
        }
        itemStacks = event.getLoot().stream().map(CraftItemStack::asNMSCopy).collect(ObjectArrayList.toList());
        // CraftBukkit end
        List<Integer> availableSlots = this.getAvailableSlots(container, random);
        this.shuffleAndSplitItems(itemStacks, availableSlots.size(), random);
        ObjectListIterator var9 = itemStacks.iterator();

        while(var9.hasNext()) {
            ItemStack itemStack = (ItemStack)var9.next();
            if (availableSlots.isEmpty()) {
                LOGGER.warn("Tried to over-fill a container");
                return;
            }

            if (itemStack.isEmpty()) {
                container.setItem(availableSlots.remove(availableSlots.size() - 1), ItemStack.EMPTY);
            } else {
                container.setItem(availableSlots.remove(availableSlots.size() - 1), itemStack);
            }
        }

    }
    @Override
    public void bridge$setCraftLootTable(CraftLootTable lootTable) {
        this.craftLootTable = lootTable;
    }

    @Override
    public CraftLootTable bridge$getCraftLootTable() {
        return this.craftLootTable;
    }
}
