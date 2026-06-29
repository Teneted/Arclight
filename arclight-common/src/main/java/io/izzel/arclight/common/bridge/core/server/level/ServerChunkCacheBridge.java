package io.izzel.arclight.common.bridge.core.server.level;

import net.minecraft.server.level.ThreadedLevelLightEngine;

import java.io.IOException;

public interface ServerChunkCacheBridge {

    default boolean bridge$tickDistanceManager() {
        throw new IllegalStateException("Not implemented");
    }

    default ThreadedLevelLightEngine bridge$getLightManager() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean isChunkLoaded(int chunkX, int chunkZ) {
        throw new IllegalStateException("Not implemented");
    }

    default void close(boolean save) throws IOException {
        throw new IllegalStateException("Not implemented");
    }

    default void purgeUnload() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setViewDistance(int viewDistance) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setSimulationDistance(int simDistance) {
        throw new IllegalStateException("Not implemented");
    }
}
