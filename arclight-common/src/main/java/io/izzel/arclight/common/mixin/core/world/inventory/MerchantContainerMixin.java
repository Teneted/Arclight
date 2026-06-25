package io.izzel.arclight.common.mixin.core.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftAbstractVillager;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(MerchantContainer.class)
public abstract class MerchantContainerMixin implements Container {

    @Shadow
    @Final
    private Merchant merchant;
    @Shadow
    @Final
    private NonNullList<ItemStack> itemStacks;
    // CraftBukkit start - add fields and methods
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public List<ItemStack> getContents() {
        return this.itemStacks;
    }

    public void onOpen(CraftHumanEntity who) {
        transaction.add(who);
    }

    public void onClose(CraftHumanEntity who) {
        transaction.remove(who);
        merchant.setTradingPlayer((Player) null); // SPIGOT-4860
    }

    public List<HumanEntity> getViewers() {
        return transaction;
    }

    @Override
    public int getMaxStackSize() {
        return maxStack;
    }

    public void setMaxStackSize(int i) {
        maxStack = i;
    }

    public org.bukkit.inventory.InventoryHolder getOwner() {
        return (merchant instanceof AbstractVillager) ? (CraftAbstractVillager) ((AbstractVillager) this.merchant).getBukkitEntity() : null;
    }

    @Override
    public Location getLocation() {
        return (merchant instanceof Villager) ? ((Villager) this.merchant).getBukkitEntity().getLocation() : null;
    }
    // CraftBukkit end
}
