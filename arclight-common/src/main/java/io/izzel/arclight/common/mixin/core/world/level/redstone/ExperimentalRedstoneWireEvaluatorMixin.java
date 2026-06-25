package io.izzel.arclight.common.mixin.core.world.level.redstone;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.ExperimentalRedstoneWireEvaluator;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.redstone.RedstoneWireEvaluator;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ExperimentalRedstoneWireEvaluator.class)
public abstract class ExperimentalRedstoneWireEvaluatorMixin extends RedstoneWireEvaluator {

    @Shadow
    private static Orientation getInitialOrientation(Level level, @Nullable Orientation incomingOrigination) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    protected abstract void calculateCurrentChanges(Level level, BlockPos initialPosition, Orientation initialOrientation);

    @Shadow
    @Final
    private Object2IntMap<BlockPos> updatedWires;

    @Shadow
    private static int unpackPower(int packed) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    protected abstract void causeNeighborUpdates(Level level);

    protected ExperimentalRedstoneWireEvaluatorMixin(RedStoneWireBlock wireBlock) {
        super(wireBlock);
    }

    /**
     * @author wdog5734
     * @reason Bukkit
     */
    @Overwrite
    public void updatePowerStrength(final Level level, final BlockPos initialPos, final BlockState ignored, final @Nullable Orientation orientation, final boolean shapeUpdateWiresAroundInitialPosition) {
        Orientation initialOrientation = getInitialOrientation(level, orientation);
        this.calculateCurrentChanges(level, initialPos, initialOrientation);
        ObjectIterator<Object2IntMap.Entry<BlockPos>> iterator = this.updatedWires.object2IntEntrySet().iterator();

        for(boolean initialWire = true; iterator.hasNext(); initialWire = false) {
            Object2IntMap.Entry<BlockPos> next = iterator.next();
            BlockPos pos = next.getKey();
            int packed = next.getIntValue();
            int newLevel = unpackPower(packed);
            BlockState state = level.getBlockState(pos);
            // CraftBukkit start
            int oldPower = ignored.getValue(RedStoneWireBlock.POWER);
            if (oldPower != newLevel) {
                BlockRedstoneEvent event = new BlockRedstoneEvent(CraftBlock.at(level, pos), oldPower, newLevel);
                level.getCraftServer().getPluginManager().callEvent(event);

                newLevel = event.getNewCurrent();
            }
            if (state.is(this.wireBlock) && oldPower != newLevel) {
                int updateFlags = 2;
                if (!shapeUpdateWiresAroundInitialPosition || !initialWire) {
                    updateFlags |= 128;
                }

                level.setBlock(pos, state.setValue(RedStoneWireBlock.POWER, newLevel), updateFlags);
            } else {
                iterator.remove();
            }
        }

        this.causeNeighborUpdates(level);
    }
}
