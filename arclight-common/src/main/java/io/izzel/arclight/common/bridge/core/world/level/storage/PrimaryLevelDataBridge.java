package io.izzel.arclight.common.bridge.core.world.level.storage;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Registry;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.dimension.LevelStem;

public interface PrimaryLevelDataBridge {

    default void setWorld(ServerLevel world) {
        throw new IllegalStateException("Not implemented");
    }

    default ServerLevel bridge$getWorld() {
        throw new IllegalStateException("Not implemented");
    }

    default LevelSettings bridge$getWorldSettings() {
        throw new IllegalStateException("Not implemented");
    }

    default Lifecycle bridge$getLifecycle() {
        throw new IllegalStateException("Not implemented");
    }

    default void checkName(String name) {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$offerCustomDimensions(Registry<LevelStem> registry) {
        throw new IllegalStateException("Not implemented");
    }

    default Tag bridge$getPdc() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPdc(Tag pdc) {
        throw new IllegalStateException("Not implemented");
    }
}
