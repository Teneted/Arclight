package io.izzel.arclight.common.bridge.core.server.level;

import io.izzel.arclight.common.mod.util.ArclightCallbackExecutor;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.function.BooleanSupplier;

public interface ChunkMapBridge {

    default void bridge$tick(BooleanSupplier hasMoreTime) {
        throw new IllegalStateException("Not implemented");
    }

    default Iterable<ChunkHolder> bridge$getLoadedChunksIterable() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$tickEntityTracker() {
        throw new IllegalStateException("Not implemented");
    }

    default ArclightCallbackExecutor bridge$getCallbackExecutor() {
        throw new IllegalStateException("Not implemented");
    }

    default ChunkHolder bridge$chunkHolderAt(long chunkPos) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setViewDistance(int i) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setChunkGenerator(ChunkGenerator generator) {
        throw new IllegalStateException("Not implemented");
    }
}
