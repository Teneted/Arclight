package io.izzel.arclight.common.bridge.core.world.level.levelgen.structure;

import org.bukkit.craftbukkit.persistence.CraftPersistentDataContainer;
import org.bukkit.event.world.AsyncStructureGenerateEvent;

public interface StructureStartBridge {

    default void bridge$setGenerateCause(AsyncStructureGenerateEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftPersistentDataContainer bridge$getPersistentDataContainer() {
        throw new IllegalStateException("Not implemented");
    }
}
