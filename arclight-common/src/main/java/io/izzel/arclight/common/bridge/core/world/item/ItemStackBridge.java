package io.izzel.arclight.common.bridge.core.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelReader;

public interface ItemStackBridge {

    default void arclight$restorePatch(DataComponentPatch datacomponentpatch) {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$setItem(Item item) {
        throw new IllegalStateException("Not implemented");
    }

    default InteractionResult bridge$forge$onItemUseFirst(UseOnContext context) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$forge$doesSneakBypassUse(LevelReader level, BlockPos pos, Player player) {
        throw new IllegalStateException("Not implemented");
    }
}
