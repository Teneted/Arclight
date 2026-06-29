package io.izzel.arclight.common.bridge.core.world.level.block;

import net.minecraft.core.BlockPos;

public interface MultifaceSpreaderSpreadPosBridge {
    default BlockPos source() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$setSource(BlockPos source) {
        throw new IllegalStateException("Not implemented");
    }
}
