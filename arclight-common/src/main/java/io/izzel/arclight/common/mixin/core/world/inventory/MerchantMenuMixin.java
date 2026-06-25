package io.izzel.arclight.common.mixin.core.world.inventory;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import org.bukkit.craftbukkit.inventory.view.CraftMerchantView;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantMenu.class)
public abstract class MerchantMenuMixin extends AbstractContainerMenu {

    @Shadow
    @Final
    private Merchant trader;
    @Shadow
    @Final
    private MerchantContainer tradeContainer;
    // CraftBukkit start
    private CraftMerchantView bukkitEntity = null;
    private Inventory player;

    @Override
    public CraftMerchantView getBukkitView() {
        if (bukkitEntity == null) {
            bukkitEntity = new CraftMerchantView(this.player.player.getBukkitEntity(), new org.bukkit.craftbukkit.inventory.CraftInventoryMerchant(trader, tradeContainer), ((MerchantMenu) (Object) this), trader);
        }
        return bukkitEntity;
    }
    // CraftBukkit end

    protected MerchantMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/MerchantMenu;addStandardInventorySlots(Lnet/minecraft/world/Container;II)V"))
    private void arclight$init(int containerId, Inventory inventory, Merchant merchant, CallbackInfo ci) {
        this.player = inventory; // CraftBukkit - save player
    }

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    public void arclight$checkReachable(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!bridge$isCheckReachable()) cir.setReturnValue(true); // CraftBukkit - checkReachable
    }

    @ModifyExpressionValue(method = "playTradeSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/trading/Merchant;isClientSide()Z"))
    private boolean arclight$checkActulTrader(boolean original) {
        return original && this.trader instanceof Entity;// CraftBukkit - SPIGOT-5035
    }
}
