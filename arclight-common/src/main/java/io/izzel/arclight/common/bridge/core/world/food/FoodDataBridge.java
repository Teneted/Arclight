package io.izzel.arclight.common.bridge.core.world.food;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public interface FoodDataBridge {

    default int bridge$getSaturatedRegenRate() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setSaturatedRegenRate(int saturatedRegenRate) {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$getUnsaturatedRegenRate() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setUnsaturatedRegenRate(int unsaturatedRegenRate) {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$getStarvationRate() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setStarvationRate(int starvationRate) {
        throw new IllegalStateException("Not implemented");
    }

    default void eat(FoodProperties foodproperties, ItemStack itemstack, ServerPlayer serverplayer) {
        throw new IllegalStateException("Not implemented");
    }


    default void bridge$setEntityHuman(ServerPlayer playerEntity) {
        throw new IllegalStateException("Not implemented");
    }

    default Player bridge$getEntityHuman() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushEatStack(ItemStack stack) {
        throw new IllegalStateException("Not implemented");
    }
}
