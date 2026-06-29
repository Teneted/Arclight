package io.izzel.arclight.common.bridge.core.core.component;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;

public interface DataComponentPatch_BuilderBridge {

    default void copy(DataComponentPatch orig) {
        throw new IllegalStateException("Not implemented");
    }

    default void clear(DataComponentType<?> type) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean isSet(DataComponentType<?> type) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean isEmpty() {
        throw new IllegalStateException("Not implemented");
    }
}
