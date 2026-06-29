package io.izzel.arclight.common.bridge.core.server.bossevents;

import org.bukkit.boss.KeyedBossBar;

public interface CustomBossEventBridge {

    default KeyedBossBar bridge$getBossBar() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridget$setBossBar(KeyedBossBar bossBar) {
        throw new IllegalStateException("Not implemented");
    }

    default KeyedBossBar getBukkitEntity() {
        throw new IllegalStateException("Not implemented");
    }
}
