package io.izzel.arclight.common.bridge.core.world.damagesource;

import net.minecraft.network.chat.Component;

public interface CombatEntryBridge {

    default void bridge$setDeathMessage(Component component) {
        throw new IllegalStateException("Not implemented");
    }

    default Component bridge$getDeathMessage() {
        throw new IllegalStateException("Not implemented");
    }
}
