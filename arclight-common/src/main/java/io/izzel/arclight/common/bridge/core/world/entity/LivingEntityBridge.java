package io.izzel.arclight.common.bridge.core.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityKnockbackEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;

public interface LivingEntityBridge extends EntityBridge {

    default void bridge$setSlot(EquipmentSlot slotIn, ItemStack stack, boolean silent) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$playEquipSound(EquipmentSlot slot, ItemStack oldItem, ItemStack newItem, boolean silent) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$canPickUpLoot() {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$getExpReward(Entity entity) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setExpToDrop(int amount) {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$getExpToDrop() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushHealReason(EntityRegainHealthEvent.RegainReason regainReason) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$heal(float healAmount, EntityRegainHealthEvent.RegainReason regainReason) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushEffectCause(EntityPotionEffectEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$addEffect(MobEffectInstance effect, EntityPotionEffectEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$removeEffect(Holder<MobEffect> effect, EntityPotionEffectEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$removeAllEffects(EntityPotionEffectEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default Optional<EntityPotionEffectEvent.Cause> bridge$getEffectCause() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushKnockbackCause(Entity attacker, EntityKnockbackEvent.KnockbackCause cause) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    default CraftLivingEntity getBukkitEntity() {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$forge$getExperienceDrop(LivingEntity entity, Player attackingPlayer, int originalExperience) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$forge$onLivingUseTotem(LivingEntity entity, DamageSource damageSource, ItemStack totem, InteractionHand hand) {
        throw new IllegalStateException("Not implemented");
    }

    enum LivingTargetType {
        BEHAVIOR_TARGET,
        MOB_TARGET
    }

    default void bridge$forge$onLivingConvert(LivingEntity entity, LivingEntity outcome) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$forge$canEntityDestroy(Level level, BlockPos pos, LivingEntity entity) {
        throw new IllegalStateException("Not implemented");
    }

    @Nullable
    default EntityDamageEvent arclight$fireEntityDamageEvent(DamageSource source, float original) {
        throw new IllegalStateException("Not implemented");
    }

    default ArrayList<org.bukkit.inventory.ItemStack> bridge$getDrops() {
        throw new IllegalStateException("Not implemented");
    }
}
