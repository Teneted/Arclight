package io.izzel.arclight.common.mixin.core.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.inventory.view.CraftBeaconView;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeaconMenu.class)
public abstract class BeaconMenuMixin extends AbstractContainerMenu {

    @Shadow
    @Final
    private Container beacon;
    // CraftBukkit start
    private CraftBeaconView bukkitEntity = null;
    private Inventory player;
    // CraftBukkit end

    protected BeaconMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method = "<init>(ILnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/BeaconMenu$1;<init>(Lnet/minecraft/world/inventory/BeaconMenu;I)V"))
    private void arclight$init(int containerId, Container inventory, ContainerData beaconData, ContainerLevelAccess access, CallbackInfo ci) {
        player = (Inventory) inventory; // CraftBukkit - TODO: check this
    }

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    public void arclight$checkReachable(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!bridge$isCheckReachable()) cir.setReturnValue(true); // CraftBukkit - checkReachable
    }

    // CraftBukkit start
    @Override
    public CraftBeaconView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        org.bukkit.craftbukkit.inventory.CraftInventoryBeacon inventory = new org.bukkit.craftbukkit.inventory.CraftInventoryBeacon(this.beacon);
        bukkitEntity = new CraftBeaconView(this.player.player.getBukkitEntity(), inventory, ((BeaconMenu) (Object) this));
        return bukkitEntity;
    }
    // CraftBukkit end
}
