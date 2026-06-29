package io.izzel.arclight.common.bridge.core.server.level;

import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;

public interface ChunkMap_TrackedEntityBridge {

    default ServerEntity bridge$getServerEntity() {
        throw new IllegalStateException("Not implemented");
    }

    default Entity bridge$getEntity() {
        throw new IllegalStateException("Not implemented");
    }

    default SectionPos bridge$getLastSectionPos() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setLastSectionPos(SectionPos pos) {
        throw new IllegalStateException("Not implemented");
    }
}
