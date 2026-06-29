package io.izzel.arclight.common.bridge.core.world.inventory;

public interface SlotBridge {

    default void bridge$onSwapCraft(int numItemsCrafted) {
        throw new IllegalStateException("Not implemented");
    }
}
