package io.izzel.arclight.common.bridge.optimization;

public interface EntityBridge_ActivationRange {

    default void inactiveTick() {
        throw new IllegalStateException("Not implemented");
    }

    default void updateActivation() {
        throw new IllegalStateException("Not implemented");
    }
}
