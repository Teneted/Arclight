package io.izzel.arclight.common.bridge.core.server.level;

import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Entity;

import java.util.Set;

public interface ServerEntityBridge {

    default void bridge$setTrackedPlayers(Set<ServerPlayerConnection> trackedPlayers) {
        throw new IllegalStateException("Not implemented");
    }

    default Entity bridge$getTrackingEntity() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$syncPosition() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$instantSyncPosition() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$instantSyncMotion() {
        throw new IllegalStateException("Not implemented");
    }
}
