package io.izzel.arclight.common.bridge.core.world.level;

import net.minecraft.world.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public interface LevelWriterBridge {

    default boolean bridge$addEntity(Entity entity, CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushAddEntityReason(CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default CreatureSpawnEvent.SpawnReason bridge$getAddEntityReason() {
        throw new IllegalStateException("Not implemented");
    }

    // CraftBukkit start
    default boolean addFreshEntity(Entity entity, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }
    // CraftBukkit end
}
