package io.izzel.arclight.common.mixin.core.world.inventory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.izzel.arclight.common.bridge.core.world.inventory.PosContainerBridge;
import io.izzel.arclight.common.mod.server.world.inventory.ArclightGrindstoneView;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftInventoryGrindstone;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrindstoneMenu.class)
public abstract class GrindstoneMenuMixin extends AbstractContainerMenu implements PosContainerBridge {

    @Shadow
    @Final
    private ContainerLevelAccess access;

    @Shadow
    @Final
    private Container repairSlots;

    @Shadow
    @Final
    private Container resultSlots;

    protected GrindstoneMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private org.bukkit.entity.Player player;

    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At("RETURN"))
    public void arclight$init(int containerId, Inventory inventory, ContainerLevelAccess access, CallbackInfo ci) {
        player = (org.bukkit.entity.Player) inventory.player.getBukkitEntity(); // CraftBukkit
    }

    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/GrindstoneMenu;broadcastChanges()V"))
    private void arclight$sync(CallbackInfo ci) {
        sendAllDataToRemote();
    }

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void arclight$checkReachable(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!this.bridge$isCheckReachable()) {
            cir.setReturnValue(true);
        }
    }

    @WrapOperation(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V"))
    private void arclight$callPrepareGrindstoneEvent(Container instance, int i, ItemStack itemStack, Operation<Void> original) {
        final CraftInventoryView<GrindstoneMenu, ?> craft = getBukkitView();
        if (craft instanceof ArclightGrindstoneView) {
            // Call prepare event; preserve injection point
            PrepareGrindstoneEvent event = new PrepareGrindstoneEvent(craft, CraftItemStack.asCraftMirror(itemStack).clone());
            event.getView().getPlayer().getServer().getPluginManager().callEvent(event);
            original.call(instance, 2, CraftItemStack.asNMSCopy(event.getResult()));
        } else {
            // Run plugin custom logic
            CraftEventFactory.callPrepareGrindstoneEvent(getBukkitView(), itemStack);
        }
    }

    @Override
    public ContainerLevelAccess bridge$getWorldPos() {
        return this.access;
    }

    @Override
    public CraftInventoryView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        CraftInventoryGrindstone inventory = new CraftInventoryGrindstone(this.repairSlots, this.resultSlots);
        bukkitEntity = new ArclightGrindstoneView(this.player, inventory, ((GrindstoneMenu) (Object) this));
        return bukkitEntity;
    }
    // CraftBukkit end
}
