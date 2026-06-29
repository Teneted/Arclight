package io.izzel.arclight.common.bridge.core.world.item.crafting;

public interface IngredientBridge {

    default void bridge$setExact(boolean exact) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isExact() {
        throw new IllegalStateException("Not implemented");
    }
}
