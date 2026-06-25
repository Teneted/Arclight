package io.izzel.arclight.common.mixin.core.world.inventory;

import io.izzel.arclight.common.bridge.core.world.inventory.ContainerLevelAccessBridge;
import io.izzel.arclight.common.bridge.core.world.level.LevelBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContainerLevelAccess.class)
public interface ContainerLevelAccessMixin extends ContainerLevelAccessBridge {

    // CraftBukkit start
    @Override
    default Level getWorld() {
        return ((ContainerLevelAccess) this).evaluate((a, b) -> a).orElse(null);
    }

    @Override
    default BlockPos getPosition() {
        return ((ContainerLevelAccess) this).evaluate((a, b) -> b).orElse(null);
    }

    @Override
    default Location getLocation() {
        BlockPos blockPos = getPosition();
        if (blockPos == null) {
            return null;
        } else {
            Level level = getWorld();
            CraftWorld world = level == null ? null : ((LevelBridge) level).getWorld();
            return new Location(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
    }
    // CraftBukkit end
}
