package io.izzel.arclight.common.bridge.core.world.entity;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.jetbrains.annotations.Nullable;

public interface MobBridge extends LivingEntityBridge {

    default void bridge$pushGoalTargetReason(EntityTargetEvent.TargetReason reason, boolean fireEvent) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushTransformReason(EntityTransformEvent.TransformReason transformReason) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$setGoalTarget(LivingEntity livingEntity, EntityTargetEvent.TargetReason reason, boolean fireEvent) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$lastGoalTargetResult() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isPersistenceRequired() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPersistenceRequired(boolean value) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setAware(boolean aware) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$captureItemDrop(ItemEntity itemEntity) {
        throw new IllegalStateException("Not implemented");
    }

    default AgeableMob bridge$forge$onBabyEntitySpawn(Mob partner, @Nullable AgeableMob proposedChild) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$common$animalTameEvent(Player player) {
        throw new IllegalStateException("Not implemented");
    }
}
