package io.izzel.arclight.common.bridge.core.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface BucketItemBridge {
    default @Nullable Direction arclight$getDirection() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$setDirection(@Nullable Direction value) {
        throw new IllegalStateException("Not implemented");
    }

    default @Nullable BlockPos arclight$getClick() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$setClick(@Nullable BlockPos value) {
        throw new IllegalStateException("Not implemented");
    }

    default @Nullable InteractionHand arclight$getHand() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$setHand(@Nullable InteractionHand value) {
        throw new IllegalStateException("Not implemented");
    }

    default @Nullable ItemStack arclight$getStack() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$setStack(@Nullable ItemStack value) {
        throw new IllegalStateException("Not implemented");
    }

    default @Nullable org.bukkit.inventory.ItemStack arclight$getCaptureItem() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$setCaptureItem(@Nullable org.bukkit.inventory.ItemStack value) {
        throw new IllegalStateException("Not implemented");
    }
}
