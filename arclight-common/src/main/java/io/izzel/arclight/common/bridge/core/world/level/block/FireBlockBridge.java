package io.izzel.arclight.common.bridge.core.world.level.block;

import net.minecraft.world.level.block.Block;

public interface FireBlockBridge {

    default boolean bridge$canBurn(Block block) {
        throw new IllegalStateException("Not implemented");
    }
}
