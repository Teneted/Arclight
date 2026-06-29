package io.izzel.arclight.common.bridge.core.world.level;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import java.util.List;

public interface ExplosionBridge {

    default Entity bridge$getExploder() {
        throw new IllegalStateException("Not implemented");
    }

    default float bridge$getSize() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setSize(float size) {
        throw new IllegalStateException("Not implemented");
    }

    default Explosion.BlockInteraction bridge$getMode() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$wasCancelled() {
        throw new IllegalStateException("Not implemented");
    }

    default float bridge$getYield() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$onExplosionDetonate(Level level, Explosion explosion, List<Entity> list, double diameter) {
        throw new IllegalStateException("Not implemented");
    }
}
