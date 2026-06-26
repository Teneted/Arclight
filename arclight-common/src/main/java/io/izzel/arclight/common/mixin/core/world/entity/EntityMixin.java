package io.izzel.arclight.common.mixin.core.world.entity;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.izzel.arclight.common.bridge.core.world.entity.EntityBridge;
import io.izzel.arclight.common.bridge.core.world.entity.InternalEntityBridge;
import io.izzel.arclight.common.bridge.optimization.EntityBridge_ActivationRange;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityBridge, EntityBridge_ActivationRange, InternalEntityBridge {

    @Shadow
    private Level level;
    @Shadow
    @Final
    public static int TOTAL_AIR_SUPPLY;
    @Shadow
    private float yRot;

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getZ();

    @Shadow
    public abstract void remove(Entity.RemovalReason reason);

    @Shadow
    public abstract int getId();

    @Shadow
    public abstract SynchedEntityData getEntityData();

    @Shadow
    public abstract void setRemoved(Entity.RemovalReason reason);

    @Shadow
    public abstract Pose getPose();

    @Shadow
    public abstract String getScoreboardName();

    @Shadow
    protected abstract void handlePortal();

    @Shadow
    public abstract boolean isInLava();

    @Shadow
    public abstract void igniteForTicks(int numberOfTicks);

    @Shadow
    private int remainingFireTicks;
    // CraftBukkit start
    private static final int CURRENT_LEVEL = 2;
    private static boolean isLevelAtLeast(ValueInput tag, int level) {
        int updateLevel = tag.getIntOr("Bukkit.updateLevel", -1);
        return updateLevel != -1 && tag.getIntOr("Bukkit.updateLevel", -1) >= level;
    }

    private CraftEntity bukkitEntity;

    @Override
    public CraftEntity getBukkitEntity() {
        if (bukkitEntity == null) {
            bukkitEntity = CraftEntity.getEntity(level.getCraftServer(), ((Entity) (Object) this));
        }
        return bukkitEntity;
    }

    // CraftBukkit - SPIGOT-6907: re-implement LivingEntity#setMaximumAir()
    @Override
    public int getDefaultMaxAirSupply() {
        return TOTAL_AIR_SUPPLY;
    }
    // CraftBukkit end

    // CraftBukkit start
    public boolean forceDrops;
    public boolean persist = true;
    public boolean visibleByDefault = true;
    public boolean valid;
    public boolean inWorld = false;
    public boolean generation;
    public int maxAirTicks = getDefaultMaxAirSupply(); // CraftBukkit - SPIGOT-6907: re-implement LivingEntity#setMaximumAir()
    public org.bukkit.projectiles.ProjectileSource projectileSource; // For projectiles only
    public boolean lastDamageCancelled; // SPIGOT-5339, SPIGOT-6252, SPIGOT-6777: Keep track if the event was canceled
    public boolean persistentInvisibility = false;
    public BlockPos lastLavaContact;
    // Marks an entity, that it was removed by a plugin via Entity#remove
    // Main use case currently is for SPIGOT-7487, preventing dropping of leash when leash is removed
    public boolean pluginRemoved = false;

    private AtomicReference<EntityRemoveEvent.Cause> arclight$removeCause = new AtomicReference<>();

    @Inject(method = "kill", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;remove(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"))
    private void arclight$pushRemoveReason(ServerLevel level, CallbackInfo ci) {
        this.bridge$pushEntityRemoveCause(EntityRemoveEvent.Cause.DEATH); // CraftBukkit - add Bukkit remove cause
    }

    @Inject(method = "discard", at = @At("HEAD"))
    public void arclight$pushRemoveReason0(CallbackInfo ci) {
        this.bridge$pushEntityRemoveCause(null); // CraftBukkit - add Bukkit remove cause
    }

    @Inject(method = "remove", at = @At("HEAD"))
    public void arclight$pushRemoveReason1(CallbackInfo ci) {
        this.bridge$pushEntityRemoveCause(null); // CraftBukkit - add Bukkit remove cause
    }

    @Override
    public final void discard(EntityRemoveEvent.Cause cause) {
        this.remove(Entity.RemovalReason.DISCARDED, cause);
    }

    @Inject(method = "setPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData;set(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V"), cancellable = true)
    private void arclight$callEntityPoseChangeEvent(Pose pose, CallbackInfo ci) {
        // CraftBukkit start
        if (pose == this.getPose()) {
            ci.cancel();
            return;
        }
        this.level.getCraftServer().getPluginManager().callEvent(new EntityPoseChangeEvent(this.getBukkitEntity(), org.bukkit.entity.Pose.values()[pose.ordinal()]));
        // CraftBukkit end
    }

    @Inject(method = "setRot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setYRot(F)V"))
    private void arclight$handleIsNaN(float yRot, float xRot, CallbackInfo ci) {
        // CraftBukkit start - yaw was sometimes set to NaN, so we need to set it back to 0
        if (Float.isNaN(yRot)) {
            yRot = 0;
        }

        if (yRot == Float.POSITIVE_INFINITY || yRot == Float.NEGATIVE_INFINITY) {
            if (((Entity) (Object) this) instanceof ServerPlayer) {
                this.level.getCraftServer().getLogger().warning(this.getScoreboardName() + " was caught trying to crash the server with an invalid yaw");
                ((CraftPlayer) this.getBukkitEntity()).kickPlayer("Infinite yaw (Hacking?)");
            }
            yRot = 0;
        }

        // pitch was sometimes set to NaN, so we need to set it back to 0
        if (Float.isNaN(xRot)) {
            xRot = 0;
        }

        if (xRot == Float.POSITIVE_INFINITY || xRot == Float.NEGATIVE_INFINITY) {
            if (((Entity) (Object) this) instanceof ServerPlayer) {
                this.level.getCraftServer().getLogger().warning(this.getScoreboardName() + " was caught trying to crash the server with an invalid pitch");
                ((CraftPlayer) this.getBukkitEntity()).kickPlayer("Infinite pitch (Hacking?)");
            }
            xRot = 0;
        }
        // CraftBukkit end

    }

    @Inject(method = "setRemoved", at = @At("HEAD"))
    private void arclight$callEntityRemoveEvent(Entity.RemovalReason reason, CallbackInfo ci) {
        CraftEventFactory.callEntityRemoveEvent(((Entity) (Object) this), arclight$removeCause.get() != null ? arclight$removeCause.get() : null);
        arclight$removeCause.set(null);
    }

    @WrapWithCondition(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;handlePortal()V"))
    private boolean arclight$checkHandlePortal(Entity self) {
        return ((Entity) (Object) this) instanceof ServerPlayer;
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;checkBelowWorld()V"))
    private void arclight$pushLastLavaContact(CallbackInfo ci) {
        if (!this.isInLava()) {
            this.lastLavaContact = null;
        }
    }

    @WrapOperation(method = "lavaIgnite", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;igniteForSeconds(F)V"))
    private void arclight$callEntityCombustByBlockEvent(Entity instance, float numberOfSeconds, Operation<Void> original) {
        // CraftBukkit start - Fallen in lava TODO: this event spams!
        if (((Entity) (Object) this) instanceof LivingEntity && remainingFireTicks <= 0) {
            // not on fire yet
            org.bukkit.block.Block damager = (lastLavaContact == null) ? null : org.bukkit.craftbukkit.block.CraftBlock.at(level, lastLavaContact);
            org.bukkit.entity.Entity damagee = this.getBukkitEntity();
            EntityCombustEvent combustEvent = new org.bukkit.event.entity.EntityCombustByBlockEvent(damager, damagee, 15);
            this.level.getCraftServer().getPluginManager().callEvent(combustEvent);

            if (!combustEvent.isCancelled()) {
                this.igniteForSeconds(combustEvent.getDuration(), false);
            }
        } else {
            // This will be called every single tick the entity is in lava, so don't throw an event
            this.igniteForSeconds(15.0F, false);
        }
        // CraftBukkit end
    }

    @Inject(method = "igniteForSeconds", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;igniteForTicks(I)V"))
    private void arclight$callEntityCombustEvent(float numberOfSeconds, CallbackInfo ci) {
        EntityCombustEvent event = new EntityCombustEvent(this.getBukkitEntity(), numberOfSeconds);
        this.level.getCraftServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        numberOfSeconds = event.getDuration();
    }

    @Override
    public final void igniteForSeconds(float numberOfSeconds, boolean callEvent) {
        if (callEvent) {
            EntityCombustEvent event = new EntityCombustEvent(this.getBukkitEntity(), numberOfSeconds);
            this.level.getCraftServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            numberOfSeconds = event.getDuration();
        }
        // CraftBukkit end
        this.igniteForTicks(Mth.floor(numberOfSeconds * 20.0F));
    }

    // CraftBukkit start
    @Override
    public void postTick() {
        // No clean way to break out of ticking once the entity has been copied to a new world, so instead we move the portalling later in the tick cycle
        if (!(((Entity) (Object) this) instanceof ServerPlayer)) {
            this.handlePortal();
        }
    }
    // CraftBukkit end

    @Override
    public void remove(Entity.RemovalReason entity_removalreason, EntityRemoveEvent.Cause cause) {
        this.setRemoved(entity_removalreason, cause);
        // CraftBukkit end
    }

    @Override
    public void bridge$pushEntityRemoveCause(EntityRemoveEvent.Cause cause) {
        this.arclight$removeCause.set(cause);
    }

    @Override
    public final void setRemoved(Entity.RemovalReason reason, EntityRemoveEvent.Cause cause) {
        CraftEventFactory.callEntityRemoveEvent(((Entity) (Object) this), cause);
        this.setRemoved(reason);
        // CraftBukkit end
    }

    // CraftBukkit start
    @Override
    public void refreshEntityData(ServerPlayer to) {
        List<SynchedEntityData.DataValue<?>> list = this.getEntityData().getNonDefaultValues();

        if (list != null) {
            to.connection.send(new ClientboundSetEntityDataPacket(this.getId(), list));
        }
    }
    // CraftBukkit end

    @Override
    public float getBukkitYaw() {
        return this.yRot;
    }

    @Override
    public boolean bridge$isForceDrops() {
        return this.forceDrops;
    }

    @Override
    public void bridge$setForceDrops(boolean b) {
        this.forceDrops = b;
    }

    @Override
    public boolean bridge$isPersist() {
        return this.persist;
    }

    @Override
    public void bridge$setPersist(boolean persist) {
        this.persist = persist;
    }

    @Override
    public void bridge$setVisibleByDefault(boolean visible) {
        this.visibleByDefault = visible;
    }

    public boolean bridge$isVisibleByDefault() {
        return this.visibleByDefault;
    }

    @Override
    public void bridge$setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean bridge$isValid() {
        return this.valid;
    }

    @Override
    public void bridge$setInWorld(boolean inWorld) {
        this.inWorld = inWorld;
    }

    @Override
    public boolean bridge$isInWorld() {
        return this.inWorld;
    }

    @Override
    public boolean bridge$isGeneration() {
        return this.generation;
    }

    @Override
    public void bridge$setGeneration(boolean generation) {
        this.generation = generation;
    }

    @Override
    public int bridge$getMaxAirTicks() {
        return this.maxAirTicks;
    }

    @Override
    public void bridge$setMaxAirTicks(int maxAirTicks) {
        this.maxAirTicks = maxAirTicks;
    }

    @Override
    public org.bukkit.projectiles.ProjectileSource bridge$getProjectileSource() {
        return this.projectileSource;
    }

    @Override
    public void bridge$setProjectileSource(org.bukkit.projectiles.ProjectileSource source) {
        this.projectileSource = source;
    }

    @Override
    public boolean bridge$isLastDamageCancelled() {
        return this.lastDamageCancelled;
    }

    @Override
    public void bridge$setLastDamageCancelled(boolean cancelled) {
        this.lastDamageCancelled = cancelled;
    }

    @Override
    public boolean bridge$isPersistentInvisibility() {
        return this.persistentInvisibility;
    }

    @Override
    public void bridge$setPersistentInvisibility(boolean persistentInvisibility) {
        this.persistentInvisibility = persistentInvisibility;
    }

    @Override
    public BlockPos bridge$getLastLavaContact() {
        return this.lastLavaContact;
    }

    @Override
    public void bridge$setLastLavaContact(BlockPos lastLavaContact) {
        this.lastLavaContact = lastLavaContact;
    }

    @Override
    public boolean bridge$isPluginRemoved() {
        return this.pluginRemoved;
    }

    @Override
    public void bridge$setPluginRemoved(boolean pluginRemoved) {
        this.pluginRemoved = pluginRemoved;
    }

    @Override
    public boolean isChunkLoaded() {
        return level.hasChunk((int) Math.floor(this.getX()) >> 4, (int) Math.floor(this.getZ()) >> 4);
    }
    // CraftBukkit end
}
