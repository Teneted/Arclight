package io.izzel.arclight.common.mixin.core.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DispenserMenu.class)
public abstract class DispenserMenuMixin extends AbstractContainerMenu {

    @Shadow
    @Final
    public Container dispenser;
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private Inventory player;
    // CraftBukkit end

    protected DispenserMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DispenserMenu;checkContainerSize(Lnet/minecraft/world/Container;I)V"))
    private void arclight$init(int containerId, Inventory inventory, Container dispenser, CallbackInfo ci) {
        // CraftBukkit start - Save player
        this.player = inventory;
        // CraftBukkit end
    }

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void arclight$checkReachable(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!this.bridge$isCheckReachable()) {
            cir.setReturnValue(true);
        }
    }

    // CraftBukkit start
    @Override
    public CraftInventoryView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        CraftInventory inventory = new CraftInventory(this.dispenser);
        bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end
}
