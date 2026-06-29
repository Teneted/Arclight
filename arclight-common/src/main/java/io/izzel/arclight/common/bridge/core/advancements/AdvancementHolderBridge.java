package io.izzel.arclight.common.bridge.core.advancements;

public interface AdvancementHolderBridge {

    default org.bukkit.advancement.Advancement toBukkit() {
        throw new IllegalStateException("Not implemented");
    }

}
