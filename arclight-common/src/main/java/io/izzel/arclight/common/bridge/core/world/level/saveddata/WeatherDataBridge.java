package io.izzel.arclight.common.bridge.core.world.level.saveddata;

import net.minecraft.server.level.ServerLevel;

public interface WeatherDataBridge {

    default void setWorld(ServerLevel world) {
        throw new IllegalStateException("Not implemented");
    }

    default ServerLevel bridge$getWorld() {
        throw new IllegalStateException("Not implemented");
    }
}
