package io.izzel.arclight.common.bridge.core.world.level.portal;

import org.bukkit.event.player.PlayerTeleportEvent;

public interface TeleportTransitionBridge {

    default void bridge$setTeleportCause(PlayerTeleportEvent.TeleportCause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default PlayerTeleportEvent.TeleportCause bridge$getTeleportCause() {
        throw new IllegalStateException("Not implemented");
    }
}
