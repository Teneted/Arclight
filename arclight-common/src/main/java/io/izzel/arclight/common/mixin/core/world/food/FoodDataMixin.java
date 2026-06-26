package io.izzel.arclight.common.mixin.core.world.food;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.izzel.arclight.common.bridge.core.world.food.FoodDataBridge;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(FoodData.class)
public abstract class FoodDataMixin implements FoodDataBridge {

    @Shadow
    public int foodLevel;

    @Shadow
    protected abstract void add(int food, float saturation);

    @Shadow
    public float saturationLevel;
    // CraftBukkit start
    public int saturatedRegenRate = 10;
    public int unsaturatedRegenRate = 80;
    public int starvationRate = 80;
    // CraftBukkit end

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I", shift = At.Shift.AFTER))
    private void arclight$callFoodLevelChangeEvent0(ServerPlayer player, CallbackInfo ci) {
        // CraftBukkit start
        org.bukkit.event.entity.FoodLevelChangeEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callFoodLevelChangeEvent(player, Math.max(this.foodLevel - 1, 0));

        if (!event.isCancelled()) {
            this.foodLevel = event.getFoodLevel();
        }

        player.connection.send(new ClientboundSetHealthPacket(player.getBukkitEntity().getScaledHealth(), this.foodLevel, this.saturationLevel));
        // CraftBukkit end
    }

    // CraftBukkit start
    @Override
    public void eat(FoodProperties foodproperties, ItemStack itemstack, ServerPlayer serverplayer) {
        int oldFoodLevel = foodLevel;

        org.bukkit.event.entity.FoodLevelChangeEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callFoodLevelChangeEvent(serverplayer, foodproperties.nutrition() + oldFoodLevel, itemstack);

        if (!event.isCancelled()) {
            this.add(event.getFoodLevel() - oldFoodLevel, foodproperties.saturation());
        }

        serverplayer.getBukkitEntity().sendHealthUpdate();
    }
    // CraftBukkit end

    private AtomicReference<ItemStack> arclight$itemstack = new AtomicReference<>();
    private AtomicReference<ServerPlayer> arclight$player = new AtomicReference<>();

    @WrapOperation(method = "eat(Lnet/minecraft/world/food/FoodProperties;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;add(IF)V"))
    private void arclight$callFoodLevelChangeEvent1(FoodData instance, int food, float saturation, Operation<Void> original, @Local(argsOnly = true) FoodProperties foodProperties) {
        int oldFoodLevel = foodLevel;
        if (arclight$itemstack.get() != null && arclight$player.get() != null) {
            org.bukkit.event.entity.FoodLevelChangeEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callFoodLevelChangeEvent(arclight$player.get(), foodProperties.nutrition() + oldFoodLevel, arclight$itemstack.get());

            if (!event.isCancelled()) {
                this.add(event.getFoodLevel() - oldFoodLevel, foodProperties.saturation());
            }

            arclight$player.get().getBukkitEntity().sendHealthUpdate();
            arclight$itemstack.set(null);
            arclight$player.set(null);
        }else {
            original.call(instance, food, saturation);
        }
    }

    @Override
    public int bridge$getSaturatedRegenRate() {
        return this.saturatedRegenRate;
    }

    @Override
    public void bridge$setSaturatedRegenRate(int saturatedRegenRate) {
        this.saturatedRegenRate = saturatedRegenRate;
    }

    @Override
    public int bridge$getUnsaturatedRegenRate() {
        return this.unsaturatedRegenRate;
    }

    @Override
    public void bridge$setUnsaturatedRegenRate(int unsaturatedRegenRate) {
        this.unsaturatedRegenRate = unsaturatedRegenRate;
    }

    @Override
    public int bridge$getStarvationRate() {
        return this.starvationRate;
    }

    @Override
    public void bridge$setStarvationRate(int starvationRate) {
        this.starvationRate = starvationRate;
    }

    @Override
    public void bridge$setEntityHuman(ServerPlayer playerEntity) {
        this.arclight$player.set(playerEntity);
    }

    @Override
    public Player bridge$getEntityHuman() {
        return this.arclight$player.get();
    }

    @Override
    public void bridge$pushEatStack(ItemStack stack) {
        this.arclight$itemstack.set(stack);
    }
}
