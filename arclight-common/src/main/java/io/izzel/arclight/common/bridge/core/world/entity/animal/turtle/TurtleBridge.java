package io.izzel.arclight.common.bridge.core.world.entity.animal.turtle;

import io.izzel.arclight.common.bridge.core.world.entity.animal.AnimalBridge;

public interface TurtleBridge extends AnimalBridge {

    default int bridge$getDigging() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setDigging(boolean digging) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setDigging(int i) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setHasEgg(boolean b) {
        throw new IllegalStateException("Not implemented");
    }
}
