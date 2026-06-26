package io.izzel.arclight.common.mixin.core.world.item.trading;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import io.izzel.arclight.common.bridge.core.world.item.trading.MerchantOfferBridge;
import io.izzel.arclight.common.mod.mixins.annotation.CreateConstructor;
import io.izzel.arclight.common.mod.mixins.annotation.ShadowConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.bukkit.craftbukkit.inventory.CraftMerchantRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(MerchantOffer.class)
public abstract class MerchantOfferMixin implements MerchantOfferBridge {

    @Shadow
    public abstract ItemStack getCostA();

    // CraftBukkit start
    private CraftMerchantRecipe bukkitHandle;

    @ShadowConstructor
    public void arclight$constructor(final ItemCost baseCostA, final Optional<ItemCost> costB, final ItemStack result, final int uses, final int maxUses, final int xp, final float priceMultiplier, final int demand) {
        throw new RuntimeException();
    }

    @CreateConstructor
    public void arclight$constructor(ItemCost baseCostA, Optional<ItemCost> costB, ItemStack result, int uses, int maxUses, int experience, float priceMultiplier, int demand, CraftMerchantRecipe bukkit) {
        arclight$constructor(baseCostA, costB, result, uses, maxUses, experience, priceMultiplier, demand);
        this.bukkitHandle = bukkit;
    }

    @WrapWithCondition(method = "take", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V", ordinal = 0))
    private boolean arclight$checkIfEmpty(ItemStack instance, int amount) {
        return !this.getCostA().isEmpty();
    }

    @Override
    public CraftMerchantRecipe asBukkit() {
        return (bukkitHandle == null) ? bukkitHandle = new CraftMerchantRecipe(((MerchantOffer) (Object) this)) : bukkitHandle;
    }
}
