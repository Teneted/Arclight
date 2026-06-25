package io.izzel.arclight.common.mixin.core.world.level.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.NeighborUpdater;
import net.minecraft.world.level.redstone.Orientation;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NeighborUpdater.class)
public interface NeighborUpdaterMixin {

    @Inject(method = "executeUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;handleNeighborChanged(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/redstone/Orientation;Z)V"), cancellable = true)
    private static void arclight$callBlockPhysicsEvent(Level level, BlockState state, BlockPos pos, Block changedBlock, Orientation orientation, boolean movedByPiston, CallbackInfo ci) {
        // CraftBukkit start
        // BUKKIT-4923: Ignore Block Physics in Chunk population.
        if (level.bridge$isPopulating()) {
            ci.cancel();
            return;
        }

        CraftWorld cworld = ((ServerLevel) level).getWorld();
        if (cworld != null) {
            BlockPhysicsEvent event = new BlockPhysicsEvent(CraftBlock.at(level, pos), CraftBlockData.fromData(state));
            ((ServerLevel) level).getCraftServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                ci.cancel();
                return;
            }
        }
        // CraftBukkit end
    }
}
