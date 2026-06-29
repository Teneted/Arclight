package io.izzel.arclight.common.bridge.core.server.level;

import java.io.IOException;
import net.minecraft.server.level.ThreadedLevelLightEngine;

public interface ServerChunkProviderBridge {

    default void bridge$close(boolean save) throws IOException {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$purgeUnload() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$tickDistanceManager() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isChunkLoaded(int x, int z) {
        throw new IllegalStateException("Not implemented");
    }

    default ThreadedLevelLightEngine bridge$getLightManager() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setViewDistance(int viewDistance) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setSimulationDistance(int simDistance) {
        throw new IllegalStateException("Not implemented");
    }
}