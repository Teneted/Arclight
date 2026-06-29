package io.izzel.arclight.common.bridge.core.server.level;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.chunk.LevelChunk;

public interface ChunkHolderBridge {

    default int bridge$getOldTicketLevel() {
        throw new IllegalStateException("Not implemented");
    }

    default LevelChunk getFullChunkNow() {
        throw new IllegalStateException("Not implemented");
    }

    default LevelChunk getFullChunkNowUnchecked() {
        throw new IllegalStateException("Not implemented");
    }

    default void callEventIfUnloading(ChunkMap chunkmap) {
        throw new IllegalStateException("Not implemented");
    }
}
