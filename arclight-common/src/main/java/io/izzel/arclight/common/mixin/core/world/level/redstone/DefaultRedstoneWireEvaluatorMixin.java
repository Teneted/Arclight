package io.izzel.arclight.common.mixin.core.world.level.redstone;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.DefaultRedstoneWireEvaluator;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.redstone.RedstoneWireEvaluator;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(DefaultRedstoneWireEvaluator.class)
public abstract class DefaultRedstoneWireEvaluatorMixin extends RedstoneWireEvaluator {

    @Shadow
    protected abstract int calculateTargetStrength(Level level, BlockPos pos);

    protected DefaultRedstoneWireEvaluatorMixin(RedStoneWireBlock wireBlock) {
        super(wireBlock);
    }

    /**
     * @author wdog5734
     * @reason Bukkit
     */
    @Overwrite
    public void updatePowerStrength(final Level level, final BlockPos pos, final BlockState state, final @Nullable Orientation orientation, final boolean skipShapeUpdates) {
        int targetStrength = this.calculateTargetStrength(level, pos);
        // CraftBukkit start
        int oldPower = state.getValue(RedStoneWireBlock.POWER);
        if (oldPower != targetStrength) {
            BlockRedstoneEvent event = new BlockRedstoneEvent(CraftBlock.at(level, pos), oldPower, targetStrength);
            level.getCraftServer().getPluginManager().callEvent(event);

            targetStrength = event.getNewCurrent();
        }
        if (oldPower != targetStrength) {
            // CraftBukkit end
            if (level.getBlockState(pos) == state) {
                level.setBlock(pos, (BlockState)state.setValue(RedStoneWireBlock.POWER, targetStrength), 2);
            }

            Set<BlockPos> toUpdate = Sets.newHashSet();
            toUpdate.add(pos);

            for(Direction direction : Direction.values()) {
                toUpdate.add(pos.relative(direction));
            }

            for(BlockPos blockPos : toUpdate) {
                level.updateNeighborsAt(blockPos, this.wireBlock);
            }
        }

    }
}
