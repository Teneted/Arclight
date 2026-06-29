package io.izzel.arclight.common.bridge.core.world.level.block;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.craftbukkit.event.CraftPortalEvent;
import org.jetbrains.annotations.Nullable;

public interface PortalInfoBridge {

    default void bridge$setPortalEventInfo(CraftPortalEvent event) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftPortalEvent bridge$getPortalEventInfo() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setWorld(ServerLevel world) {
        throw new IllegalStateException("Not implemented");
    }

    default @Nullable ServerLevel bridge$getWorld() {
        throw new IllegalStateException("Not implemented");
    }
}
