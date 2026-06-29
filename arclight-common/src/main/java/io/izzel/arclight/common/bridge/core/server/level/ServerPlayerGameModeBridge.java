package io.izzel.arclight.common.bridge.core.server.level;

import io.izzel.arclight.common.mod.util.ArclightCaptures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface ServerPlayerGameModeBridge {

    default boolean bridge$isFiredInteract() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setFiredInteract(boolean b) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$getInteractResult() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setInteractResult(boolean b) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$handleBlockDrop(ArclightCaptures.BlockBreakEventContext breakEventContext, BlockPos pos) {
        throw new IllegalStateException("Not implemented");
    }

    default BlockPos bridge$getInteractPosition() {
        throw new IllegalStateException("Not implemented");
    }

    default InteractionHand bridge$getInteractHand() {
        throw new IllegalStateException("Not implemented");
    }

    default ItemStack bridge$getInteractItemStack() {
        throw new IllegalStateException("Not implemented");
    }
}
