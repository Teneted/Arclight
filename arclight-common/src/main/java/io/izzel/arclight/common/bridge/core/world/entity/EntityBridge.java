package io.izzel.arclight.common.bridge.core.world.entity;

import io.izzel.arclight.common.bridge.core.commands.CommandSourceBridge;
import io.izzel.arclight.common.mod.server.entity.ArclightSpawnReason;
import io.izzel.tools.product.Product;
import io.izzel.tools.product.Product4;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueOutput;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.event.CraftPortalEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface EntityBridge extends CommandSourceBridge {

    default float getBukkitYaw() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean isChunkLoaded() {
        throw new IllegalStateException("Not implemented");
    }

    default int getDefaultMaxAirSupply() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setOnFire(float seconds, boolean callEvent) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftEntity getBukkitEntity() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setBukkitEntity(CraftEntity craftEntity) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isPersist() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPersist(boolean persist) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isValid() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setValid(boolean valid) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setVisibleByDefault(boolean visible) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isVisibleByDefault() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isInWorld() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setInWorld(boolean inWorld) {
        throw new IllegalStateException("Not implemented");
    }

    default ProjectileSource bridge$getProjectileSource() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setProjectileSource(ProjectileSource projectileSource) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isChunkLoaded() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isLastDamageCancelled() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setLastDamageCancelled(boolean cancelled) {
        throw new IllegalStateException("Not implemented");
    }

    default List<Entity> bridge$getPassengers() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setRideCooldown(int rideCooldown) {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$getRideCooldown() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setLastLavaContact(BlockPos pos) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$revive() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushEntityRemoveCause(EntityRemoveEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftPortalEvent bridge$callPortalEvent(Entity entity, Location exit, PlayerTeleportEvent.TeleportCause cause, int searchRadius, int creationRadius) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$pluginRemoved() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isForceDrops() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setForceDrops(boolean b) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$forge$isPartEntity() {
        return this instanceof EnderDragonPart;
    }

    default Entity bridge$forge$getParent() {
        return this instanceof EnderDragonPart part ? part.parentMob : null;
    }

    default Entity[] bridge$forge$getParts() {
        return this instanceof EnderDragon dragon ? dragon.subEntities : null;
    }

    default Product4<Boolean /* Cancelled */, Double /* X */, Double /* Y */, Double /* Z */>
    bridge$onEntityTeleportCommand(double x, double y, double z) {
        return Product.of(false, x, y, z);
    }

    default boolean bridge$forge$canUpdate() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$pushAddEntityReason(CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default CreatureSpawnEvent.SpawnReason arclight$getAddEntityReason() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$pushExtraSpawnReason(ArclightSpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default ArclightSpawnReason arclight$getExtraSpawnReason() {
        throw new IllegalStateException("Not implemented");
    }

    default ItemEntity arclight$spawnAtLocationNoAdd(ItemStack stack, float yOffset) {
        throw new IllegalStateException("Not implemented");
    }

    default ItemEntity arclight$spawnAtLocationNoAdd(ItemStack stack) {
        return arclight$spawnAtLocationNoAdd(stack, 0f);
    }

    default boolean bridge$isGeneration() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setGeneration(boolean generation) {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$getMaxAirTicks() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setMaxAirTicks(int maxAirTicks) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isPersistentInvisibility() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPersistentInvisibility(boolean persistentInvisibility) {
        throw new IllegalStateException("Not implemented");
    }

    default BlockPos bridge$getLastLavaContact() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isPluginRemoved() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPluginRemoved(boolean pluginRemoved) {
        throw new IllegalStateException("Not implemented");
    }

    default void discard(EntityRemoveEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default void refreshEntityData(ServerPlayer to) {
        throw new IllegalStateException("Not implemented");
    }

    default void remove(Entity.RemovalReason entity_removalreason, EntityRemoveEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default void postTick() {
        throw new IllegalStateException("Not implemented");
    }

    default void setRemoved(Entity.RemovalReason reason, EntityRemoveEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default void igniteForSeconds(float numberOfSeconds, boolean callEvent) {
        throw new IllegalStateException("Not implemented");
    }

    default SoundEvent getSwimSound0() {
        throw new IllegalStateException("Not implemented");
    }

    default SoundEvent getSwimSplashSound0() {
        throw new IllegalStateException("Not implemented");
    }

    default SoundEvent getSwimHighSpeedSplashSound0() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean canCollideWithBukkit(Entity entity) {
        throw new IllegalStateException("Not implemented");
    }

    default void saveWithoutId(ValueOutput output, boolean includeAll) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean saveAsPassenger(ValueOutput output, boolean includeAll) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean dropAllLeashConnections(@Nullable Player player, EntityUnleashEvent.UnleashReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$pushUnleashReason(EntityUnleashEvent.UnleashReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    /**
     * Called when an Entity is added to a ServerLevel via {@link net.minecraft.server.level.ServerLevel#addEntity(Entity)}.
     * If entity is discarded before it can enter the level, the remove event will be wrongly sent (before it's actually added).
     * And in the case when used by world generation, the server may crash for triggering {@link org.bukkit.event.entity.EntityRemoveEvent}
     * asynchronously.
     * We maintain whether it's "in the level" here, recording whether the event has been sent, with the assumption that an entity
     * is only removed from the main thread, once it's added to the world. This will solve the problem above and more potential problems.
     */
    @SuppressWarnings("JavadocReference")
    default void arclight$onAddedToLevel() {
        throw new IllegalStateException("Not implemented");
    }
}
