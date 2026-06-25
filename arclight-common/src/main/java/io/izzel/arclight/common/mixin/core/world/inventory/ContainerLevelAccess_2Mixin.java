package io.izzel.arclight.common.mixin.core.world.inventory;

import io.izzel.arclight.common.bridge.core.world.inventory.ContainerLevelAccessBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.world.inventory.ContainerLevelAccess$2")
public class ContainerLevelAccess_2Mixin implements ContainerLevelAccessBridge {

    @Shadow
    @Final
    Level val$level;

    @Shadow
    @Final
    BlockPos val$pos;

    // CraftBukkit start
    @Override
    public Level getWorld() {
        return val$level;
    }

    @Override
    public BlockPos getPosition() {
        return val$pos;
    }
    // CraftBukkit end
}
