package io.izzel.arclight.common.bridge.core.world.level.levelgen.flat;

import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public interface FlatLevelGeneratorSettingsBridge {
    default void bridge$setBiomeSource(BiomeSource biomeSource) {
        throw new IllegalStateException("Not implemented");
    }

    default FlatLevelGeneratorSettings bridge$withBiomeSource(BiomeSource biomeSource) {
        throw new IllegalStateException("Not implemented");
    }

    default BiomeSource bridge$getBiomeSource() {
        throw new IllegalStateException("Not implemented");
    }
}
