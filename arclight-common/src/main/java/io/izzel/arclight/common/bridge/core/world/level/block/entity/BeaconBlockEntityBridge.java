package io.izzel.arclight.common.bridge.core.world.level.block.entity;

import org.bukkit.potion.PotionEffect;

public interface BeaconBlockEntityBridge {

    default PotionEffect bridge$getPrimaryEffect() {
        throw new IllegalStateException("Not implemented");
    }

    default PotionEffect bridge$getSecondaryEffect() {
        throw new IllegalStateException("Not implemented");
    }
}
