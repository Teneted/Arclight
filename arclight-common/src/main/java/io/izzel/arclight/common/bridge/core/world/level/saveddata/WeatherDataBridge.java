package io.izzel.arclight.common.bridge.core.world.level.saveddata;

import net.minecraft.server.level.ServerLevel;

public interface WeatherDataBridge {

    default void setWorld(ServerLevel world) {

    }

    default ServerLevel bridge$getWorld() {
        return null;
    }
}
