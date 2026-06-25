package io.izzel.arclight.common.mixin.core.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.inventory.view.CraftBrewingStandView;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandMenu.class)
public abstract class BrewingStandMenuMixin extends AbstractContainerMenu {

    @Shadow
    @Final
    private Container brewingStand;
    // CraftBukkit start
    private CraftBrewingStandView bukkitEntity = null;
    private Inventory player;
    // CraftBukkit end

    protected BrewingStandMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method =  "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/BrewingStandMenu;checkContainerSize(Lnet/minecraft/world/Container;I)V"))
    private void arclight$init(int containerId, Inventory inventory, Container brewingStand, ContainerData brewingStandData, CallbackInfo ci) {
        player = inventory; // CraftBukkit
    }

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void arclight$checkReachable(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!this.bridge$isCheckReachable()) cir.setReturnValue(true); // CraftBukkit
    }

    // CraftBukkit start
    @Override
    public CraftBrewingStandView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        CraftInventoryBrewer inventory = new CraftInventoryBrewer(this.brewingStand);
        bukkitEntity = new CraftBrewingStandView(this.player.player.getBukkitEntity(), inventory, ((BrewingStandMenu) (Object) this));
        return bukkitEntity;
    }
    // CraftBukkit end

}
