package io.izzel.arclight.common.bridge.core.world.entity.vehicle.minecart;

public interface AbstractMinecartBridge {

    default boolean bridge$forge$canUseRail() {
        throw new IllegalStateException("Not implemented");
    }
}
