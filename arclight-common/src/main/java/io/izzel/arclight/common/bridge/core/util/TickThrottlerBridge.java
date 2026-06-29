package io.izzel.arclight.common.bridge.core.util;

public interface TickThrottlerBridge {

    default boolean isIncrementAndUnderThreshold() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean isIncrementAndUnderThreshold(int incrementStep, int threshold) {
        throw new IllegalStateException("Not implemented");
    }
}
