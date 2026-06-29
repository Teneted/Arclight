package io.izzel.arclight.common.bridge.core.world.level.storage;

import java.io.File;
import java.util.Optional;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public interface PlayerDataStorageBridge {

    default Optional<CompoundTag> load(Player player) {
        throw new IllegalStateException("Not implemented");
    }

    default File getPlayerDir() {
        throw new IllegalStateException("Not implemented");
    }
}
