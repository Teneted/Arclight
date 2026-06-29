package io.izzel.arclight.common.bridge.core.world.attribute;

import org.bukkit.event.player.PlayerBedEnterEvent;

public interface BedRuleBridge {

    default PlayerBedEnterEvent.BedEnterResult bukkit() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushBedEnterResult(PlayerBedEnterEvent.BedEnterResult bukkit) {
        throw new IllegalStateException("Not implemented");
    }
}
