package io.izzel.arclight.common.bridge.core.world.level.storage;

import java.io.File;
import java.util.Optional;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public interface PlayerDataStorageBridge {

    default Optional<CompoundTag> load(Player player) {
        return Optional.empty();
    }

    default File getPlayerDir() {
        return null;
    }
}
