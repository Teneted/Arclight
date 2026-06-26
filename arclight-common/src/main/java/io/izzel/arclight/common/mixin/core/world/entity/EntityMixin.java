package io.izzel.arclight.common.mixin.core.world.entity;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Cancellable;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import io.izzel.arclight.common.bridge.core.world.entity.EntityBridge;
import io.izzel.arclight.common.bridge.core.world.entity.InternalEntityBridge;
import io.izzel.arclight.common.bridge.optimization.EntityBridge_ActivationRange;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityBridge, EntityBridge_ActivationRange, InternalEntityBridge {

    // @formatter:off
    @Shadow private Level level;
    @Shadow @Final public static int TOTAL_AIR_SUPPLY;
    @Shadow private float yRot;
    @Shadow public abstract double getX();
    @Shadow public abstract double getZ();
    @Shadow public abstract void remove(Entity.RemovalReason reason);
    @Shadow public abstract int getId();
    @Shadow public abstract SynchedEntityData getEntityData();
    @Shadow public abstract void setRemoved(Entity.RemovalReason reason);
    @Shadow public abstract Pose getPose();
    @Shadow public abstract String getScoreboardName();
    @Shadow protected abstract void handlePortal();
    @Shadow public abstract boolean isInLava();
    @Shadow public abstract void igniteForTicks(int numberOfTicks);
    @Shadow private int remainingFireTicks;
    @Shadow public boolean horizontalCollision;
    @Shadow public abstract double getY();
    @Shadow protected abstract SoundEvent getSwimHighSpeedSplashSound();
    @Shadow protected abstract SoundEvent getSwimSplashSound();
    @Shadow protected abstract SoundEvent getSwimSound();
    @Shadow public abstract boolean isPushable();
    @Shadow protected abstract void addAdditionalSaveData(ValueOutput output);
    @Shadow public abstract void fillCrashReportCategory(CrashReportCategory category);
    @Shadow public abstract List<Entity> getPassengers();
    @Shadow public abstract boolean isVehicle();
    @Shadow private CustomData customData;
    @Shadow @Final private Set<String> tags;
    @Shadow @Final private static Codec<List<String>> TAG_LIST_CODEC;
    @Shadow public boolean hasVisualFire;
    @Shadow public abstract int getTicksFrozen();
    @Shadow private boolean hasGlowingTag;
    @Shadow public abstract boolean isNoGravity();
    @Shadow public abstract boolean isSilent();
    @Shadow public abstract boolean isCustomNameVisible();
    @Shadow public abstract UUID getUUID();
    @Shadow public abstract @Nullable Component getCustomName();
    @Shadow public int portalCooldown;
    @Shadow private boolean invulnerable;
    @Shadow public abstract boolean onGround();
    @Shadow public abstract int getAirSupply();
    @Shadow public double fallDistance;
    @Shadow public abstract float getXRot();
    @Shadow public abstract float getYRot();
    @Shadow public abstract Vec3 getDeltaMovement();
    @Shadow public abstract Vec3 position();
    @Shadow private @Nullable Entity vehicle;
    @Shadow private float xRot;
    @Shadow public abstract int getMaxAirSupply();
    @Shadow private Entity.@Nullable RemovalReason removalReason;
    @Shadow public abstract @Nullable String getEncodeId();
    @Shadow public abstract void setInvisible(boolean invisible);
    @Shadow public abstract void gameEvent(Holder<GameEvent> event, @Nullable Entity sourceEntity);
    @Shadow public abstract Level level();
    // @formatter:on

    @Shadow
    public abstract boolean isSwimming();

    @Shadow
    @Final
    protected SynchedEntityData entityData;
    @Shadow
    @Final
    private static EntityDataAccessor<Integer> DATA_AIR_SUPPLY_ID;
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

    @Inject(method = "onBelowWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;discard()V"))
    private void arclight$discardOnBelowWorld(CallbackInfo ci) {
        this.bridge$pushEntityRemoveCause(EntityRemoveEvent.Cause.OUT_OF_WORLD); // CraftBukkit - add Bukkit remove cause
    }

    @ModifyArg(method = "lavaHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private DamageSource arclight$putLastLavaContactDmgSrc(DamageSource source) {
        return source.directBlock(level, lastLavaContact);
    }

    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z"))
    private void arclight$callVehicleBlockCollisionEvent(MoverType moverType, Vec3 delta, CallbackInfo ci, @Local(ordinal = 1) Vec3 movement) {
        // CraftBukkit start
        if (horizontalCollision && getBukkitEntity() instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) this.getBukkitEntity();
            org.bukkit.block.Block bl = this.level.getWorld().getBlockAt(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()));

            if (delta.x > movement.x) {
                bl = bl.getRelative(BlockFace.EAST);
            } else if (delta.x < movement.x) {
                bl = bl.getRelative(BlockFace.WEST);
            } else if (delta.z > movement.z) {
                bl = bl.getRelative(BlockFace.SOUTH);
            } else if (delta.z < movement.z) {
                bl = bl.getRelative(BlockFace.NORTH);
            }

            if (!bl.getType().isAir()) {
                VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, bl);
                level.getCraftServer().getPluginManager().callEvent(event);
            }
        }
        // CraftBukkit end
    }

    @Inject(method = "absSnapTo(DDD)V", at = @At("RETURN"))
    private void arclight$checkValid(double x, double y, double z, CallbackInfo ci) {
        if (valid) level.getChunk((int) Math.floor(this.getX()) >> 4, (int) Math.floor(this.getZ()) >> 4); // CraftBukkit
    }

    @Definition(id = "id", local = @Local(type = String.class, name = "id"))
    @Expression("id == null")
    @ModifyExpressionValue(method = "saveAsPassenger", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean arclight$persistFlag(boolean original) {
        return !this.persist || original;
    }

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ValueOutput;store(Ljava/lang/String;Lcom/mojang/serialization/Codec;Ljava/lang/Object;)V", ordinal = 1))
    private void arclight$checkNaN(ValueOutput output, CallbackInfo ci) {
        // CraftBukkit start - Checking for NaN pitch/yaw and resetting to zero
        // TODO: make sure this is the best way to address this.
        if (Float.isNaN(this.yRot)) {
            this.yRot = 0;
        }

        if (Float.isNaN(this.xRot)) {
            this.xRot = 0;
        }
        // CraftBukkit end
    }

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ValueOutput;storeNullable(Ljava/lang/String;Lcom/mojang/serialization/Codec;Ljava/lang/Object;)V"))
    private void arclight$selectivelySave(ValueOutput output, CallbackInfo ci) {
        // PAIL: Check above UUID reads 1.8 properly, ie: UUIDMost / UUIDLeast
        output.putLong("WorldUUIDLeast", ((ServerLevel) this.level).getWorld().getUID().getLeastSignificantBits());
        output.putLong("WorldUUIDMost", ((ServerLevel) this.level).getWorld().getUID().getMostSignificantBits());
        output.putInt("Bukkit.updateLevel", CURRENT_LEVEL);
        if (!this.persist) {
            output.putBoolean("Bukkit.persist", this.persist);
        }
        if (!this.visibleByDefault) {
            output.putBoolean("Bukkit.visibleByDefault", this.visibleByDefault);
        }
        if (this.persistentInvisibility) {
            output.putBoolean("Bukkit.invisible", this.persistentInvisibility);
        }
        // SPIGOT-6907: re-implement LivingEntity#setMaximumAir()
        if (maxAirTicks != getDefaultMaxAirSupply()) {
            output.putInt("Bukkit.MaxAirSupply", getMaxAirSupply());
        }
        // CraftBukkit end
    }

    @Inject(method = "saveWithoutId", at = @At("RETURN"))
    private void arclight$storeBukkitValues(ValueOutput output, CallbackInfo ci) {
        // CraftBukkit start - stores eventually existing bukkit values
        if (this.bukkitEntity != null) {
            this.bukkitEntity.storeBukkitValues(output);
        }
        // CraftBukkit end
    }

    @Inject(method = "load", at = @At("RETURN"))
    private void arclight$loadInfo(ValueInput input, CallbackInfo ci) {
        // CraftBukkit start
        this.persist = input.getBooleanOr("Bukkit.persist", this.persist);
        this.visibleByDefault = input.getBooleanOr("Bukkit.visibleByDefault", this.visibleByDefault);
        // SPIGOT-6907: re-implement LivingEntity#setMaximumAir()
        this.maxAirTicks = input.getIntOr("Bukkit.MaxAirSupply", this.maxAirTicks);
        // CraftBukkit end

        // CraftBukkit start - Reset world
        if (((Entity) (Object) this) instanceof ServerPlayer) {
            Server server = Bukkit.getServer();
            org.bukkit.World bworld = null;

            // TODO: Remove World related checks, replaced with WorldUID
            String worldName = input.getStringOr("world", "");

            Optional<Long> most = input.getLong("WorldUUIDMost");
            Optional<Long> least = input.getLong("WorldUUIDLeast");
            if (most.isPresent() && least.isPresent()) {
                UUID uid = new UUID(most.get(), least.get());
                bworld = server.getWorld(uid);
            } else {
                bworld = server.getWorld(worldName);
            }

            if (bworld == null) {
                bworld = ((org.bukkit.craftbukkit.CraftServer) server).getServer().getLevel(Level.OVERWORLD).getWorld();
            }

            ((ServerPlayer) (Object) this).setLevel(bworld == null ? null : ((CraftWorld) bworld).getHandle());
        }
        this.getBukkitEntity().readBukkitValues(input);
        boolean bukkitInvisible = input.getBooleanOr("Bukkit.invisible", false);
        if (bukkitInvisible) {
            this.setInvisible(bukkitInvisible);
            this.persistentInvisibility = bukkitInvisible;
        }
        // CraftBukkit end

    }

    @Inject(method = "spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"), cancellable = true)
    private void arclight$captureDrops(ServerLevel level, ItemStack itemStack, Vec3 offset, CallbackInfoReturnable<ItemEntity> cir) {
        // CraftBukkit start - Capture drops for death event
        if (((Entity) (Object) this) instanceof LivingEntity && !((LivingEntity) (Object) this).bridge$isForceDrops()) {
            ((LivingEntity) (Object) this).bridge$getDrops().add(org.bukkit.craftbukkit.inventory.CraftItemStack.asBukkitCopy(itemStack));
            cir.setReturnValue(null);
        }
        // CraftBukkit end
    }

    @Inject(method = "spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), cancellable = true)
    private void arclight$callEntityDropItemEvent(ServerLevel level, ItemStack itemStack, Vec3 offset, CallbackInfoReturnable<ItemEntity> cir, @Local ItemEntity entity) {
        // CraftBukkit start
        EntityDropItemEvent event = new EntityDropItemEvent(this.getBukkitEntity(), (org.bukkit.entity.Item) entity.getBukkitEntity());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            cir.setReturnValue(null);
        }
        // CraftBukkit end
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z"), cancellable = true)
    private void arclight$callPlayerUnleashEntityEvent(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> cir, @Local Leashable leashable) {
        // CraftBukkit start - fire PlayerUnleashEntityEvent
        if (CraftEventFactory.callPlayerUnleashEntityEvent(((Entity) (Object) this), player, hand).isCancelled()) {
            ((ServerPlayer) player).connection.send(new ClientboundSetEntityLinkPacket(((Entity) (Object) this), leashable.getLeashHolder()));
            cir.setReturnValue(InteractionResult.PASS);
        }
        // CraftBukkit end
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;isLeashed()Z"), cancellable = true)
    private void arclight$callPlayerLeashEntityEvent(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> cir, @Local Leashable leashable) {
        // CraftBukkit start - fire PlayerLeashEntityEvent
        if (CraftEventFactory.callPlayerLeashEntityEvent(((Entity) (Object) this), player, player, hand).isCancelled()) {
            ((ServerPlayer) player).resendItemInHands(); // SPIGOT-7615: Resend to fix client desync with used item
            ((ServerPlayer) player).connection.send(new ClientboundSetEntityLinkPacket(((Entity) (Object) this), leashable.getLeashHolder()));
            cir.setReturnValue(InteractionResult.PASS);
        }
        // CraftBukkit end
    }

    @Unique
    private AtomicReference<EntityUnleashEvent.UnleashReason> arclight$unleashReason = new AtomicReference<>(null);

    @Inject(method = "dropAllLeashConnections", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;dropLeash()V", ordinal = 0))
    private void arclight$callEntityUnleashEvent(Player player, CallbackInfoReturnable<Boolean> cir) {
        this.level().getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), arclight$unleashReason.get() != null ? arclight$unleashReason.get() : EntityUnleashEvent.UnleashReason.UNKNOWN)); // CraftBukkit
        arclight$unleashReason.set(null);
    }

    @Inject(method = "shearOffAllLeashConnections", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;dropAllLeashConnections(Lnet/minecraft/world/entity/player/Player;)Z"))
    private void arclight$unleashReasonShear(Player player, CallbackInfoReturnable<Boolean> cir) {
        arclight$unleashReason.set(EntityUnleashEvent.UnleashReason.SHEAR);
    }

    @Inject(method = "dropAllLeashConnections", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;dropLeash()V", ordinal = 1))
    private void arclight$callEntityUnleashEvent0(Player player, CallbackInfoReturnable<Boolean> cir, @Local Leashable leashable) {
        // CraftBukkit start
        if (leashable instanceof Entity entity) {
            this.level().getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(entity.getBukkitEntity(), arclight$unleashReason.get() != null ? arclight$unleashReason.get() : EntityUnleashEvent.UnleashReason.UNKNOWN));
        }
        arclight$unleashReason.set(null);
        // CraftBukkit end
    }

    private AtomicBoolean arclight$removePassenger = new AtomicBoolean(true);

    @Inject(method = "removePassenger", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;size()I"))
    private void arclight$callEntityEvents(Entity passenger, CallbackInfo ci) {
        // CraftBukkit start
        CraftEntity craft = (CraftEntity) passenger.getBukkitEntity().getVehicle();
        Entity orig = craft == null ? null : craft.getHandle();
        if (getBukkitEntity() instanceof Vehicle && passenger.getBukkitEntity() instanceof org.bukkit.entity.LivingEntity) {
            VehicleExitEvent event = new VehicleExitEvent(
                    (Vehicle) getBukkitEntity(),
                    (org.bukkit.entity.LivingEntity) passenger.getBukkitEntity()
            );
            // Suppress during worldgen
            if (this.valid) {
                Bukkit.getPluginManager().callEvent(event);
            }
            CraftEntity craftn = (CraftEntity) passenger.getBukkitEntity().getVehicle();
            Entity n = craftn == null ? null : craftn.getHandle();
            if (event.isCancelled() || n != orig) {
                arclight$removePassenger.set(false);
            }
        }

        EntityDismountEvent event = new EntityDismountEvent(passenger.getBukkitEntity(), this.getBukkitEntity());
        // Suppress during worldgen
        if (this.valid) {
            Bukkit.getPluginManager().callEvent(event);
        }
        if (event.isCancelled()) {
            arclight$removePassenger.set(false);
        }
        // CraftBukkit end
    }

    @ModifyExpressionValue(method = "startRiding(Lnet/minecraft/world/entity/Entity;ZZ)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;canSerialize()Z"))
    private boolean arclight$checkForce(boolean original, @Local(ordinal = 0, argsOnly = true) boolean force) {
        return !force && original;
    }

    @Inject(method = "startRiding(Lnet/minecraft/world/entity/Entity;ZZ)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isPassenger()Z"), cancellable = true)
    private void arclight$callEntityEvents0(Entity entityToRide, boolean force, boolean sendEventAndTriggers, CallbackInfoReturnable<Boolean> cir) {
        // CraftBukkit start
        if (entityToRide.getBukkitEntity() instanceof Vehicle && this.getBukkitEntity() instanceof org.bukkit.entity.LivingEntity) {
            VehicleEnterEvent event = new VehicleEnterEvent((Vehicle) entityToRide.getBukkitEntity(), this.getBukkitEntity());
            // Suppress during worldgen
            if (this.valid) {
                Bukkit.getPluginManager().callEvent(event);
            }
            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }
        }

        EntityMountEvent event = new EntityMountEvent(this.getBukkitEntity(), entityToRide.getBukkitEntity());
        // Suppress during worldgen
        if (this.valid) {
            Bukkit.getPluginManager().callEvent(event);
        }
        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
        // CraftBukkit end
    }

    @Inject(method = "removeVehicle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getRemovalReason()Lnet/minecraft/world/entity/Entity$RemovalReason;"), cancellable = true)
    private void arclight$setActualVehicle(CallbackInfo ci, @Local Entity oldVehicle) {
        if (!arclight$removePassenger.get()) {
            this.vehicle = oldVehicle;
            ci.cancel();
            return;
        }
    }

    @Inject(method = "removePassenger", at = @At("TAIL"))
    private void arclight$setRemovePassenger(Entity passenger, CallbackInfo ci) {
        arclight$removePassenger.set(true);
    }

    @ModifyExpressionValue(method = "handlePortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;isAllowedToEnterPortal(Lnet/minecraft/world/level/Level;)Z"))
    private boolean arclight$allowCallEvents(boolean original, @Local(ordinal = 0) ServerLevel newLevel) {
        return (((Entity) (Object) this) instanceof ServerPlayer && newLevel == null) || (newLevel != null && original);
    }


    @Inject(method = "setSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSharedFlag(IZ)V"), cancellable = true)
    private void arclight$callToggleSwimEvent(boolean swimming, CallbackInfo ci) {
        // CraftBukkit start
        if (valid && this.isSwimming() != swimming && ((Entity) (Object) this) instanceof LivingEntity) {
            if (CraftEventFactory.callToggleSwimEvent((LivingEntity) (Object) this, swimming).isCancelled()) {
                ci.cancel();
                return;
            }
        }
        // CraftBukkit end
    }

    @WrapWithCondition(method = "setInvisible", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSharedFlag(IZ)V"))
    private boolean arclight$persistentInvisibilityCheck(Entity instance, int flag, boolean value) {
        return !this.persistentInvisibility;
    }

    @ModifyConstant(method = "getMaxAirSupply", constant = @Constant(intValue = 300))
    private int arclight$useMaxAirTicks(int constant) {
        return maxAirTicks;
    }

    @Redirect(method = "setAirSupply", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData;set(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V"))
    private <T> void arclight$callEntityAirChangeEvent(SynchedEntityData instance, EntityDataAccessor<T> accessor, T value, @Cancellable CallbackInfo ci, @Local(argsOnly = true) int supply) {
        // CraftBukkit start
        EntityAirChangeEvent event = new EntityAirChangeEvent(this.getBukkitEntity(), supply);
        // Suppress during worldgen
        if (this.valid) {
            event.getEntity().getServer().getPluginManager().callEvent(event);
        }
        if (event.isCancelled() && this.getAirSupply() != supply) {
            this.entityData.markDirty(DATA_AIR_SUPPLY_ID);
            ci.cancel();
        }
        this.entityData.set(DATA_AIR_SUPPLY_ID, event.getAmount());
        // CraftBukkit end
    }

    @Override
    public void arclight$pushUnleashReason(EntityUnleashEvent.UnleashReason reason) {
        arclight$unleashReason.set(reason);
    }

    @Override
    public boolean saveAsPassenger(ValueOutput output, boolean includeAll) {
        if (this.removalReason != null && !this.removalReason.shouldSave()) {
            return false;
        } else {
            String id = this.getEncodeId();
            if (!this.persist || id == null) { // CraftBukkit - persist flag
                return false;
            } else {
                output.putString("id", id);
                this.saveWithoutId(output, includeAll); // CraftBukkit - pass on includeAll
                return true;
            }
        }
    }

    @Override
    public void saveWithoutId(ValueOutput output, boolean includeAll) {
        try {
            // CraftBukkit start - selectively save position
            if (includeAll) {
                if (this.vehicle != null) {
                    output.store("Pos", Vec3.CODEC, new Vec3(this.vehicle.getX(), this.getY(), this.vehicle.getZ()));
                } else {
                    output.store("Pos", Vec3.CODEC, this.position());
                }
            }
            // CraftBukkit end

            output.store("Motion", Vec3.CODEC, this.getDeltaMovement());
            // CraftBukkit start - Checking for NaN pitch/yaw and resetting to zero
            // TODO: make sure this is the best way to address this.
            if (Float.isNaN(this.yRot)) {
                this.yRot = 0;
            }

            if (Float.isNaN(this.xRot)) {
                this.xRot = 0;
            }
            // CraftBukkit end
            output.store("Rotation", Vec2.CODEC, new Vec2(this.getYRot(), this.getXRot()));
            output.putDouble("fall_distance", this.fallDistance);
            output.putShort("Fire", (short)this.remainingFireTicks);
            output.putShort("Air", (short)this.getAirSupply());
            output.putBoolean("OnGround", this.onGround());
            output.putBoolean("Invulnerable", this.invulnerable);
            output.putInt("PortalCooldown", this.portalCooldown);
            // CraftBukkit start - selectively save uuid and world
            if (includeAll) {
                output.store("UUID", UUIDUtil.CODEC, this.getUUID());
                // PAIL: Check above UUID reads 1.8 properly, ie: UUIDMost / UUIDLeast
                output.putLong("WorldUUIDLeast", ((ServerLevel) this.level).getWorld().getUID().getLeastSignificantBits());
                output.putLong("WorldUUIDMost", ((ServerLevel) this.level).getWorld().getUID().getMostSignificantBits());
            }
            output.putInt("Bukkit.updateLevel", CURRENT_LEVEL);
            if (!this.persist) {
                output.putBoolean("Bukkit.persist", this.persist);
            }
            if (!this.visibleByDefault) {
                output.putBoolean("Bukkit.visibleByDefault", this.visibleByDefault);
            }
            if (this.persistentInvisibility) {
                output.putBoolean("Bukkit.invisible", this.persistentInvisibility);
            }
            // SPIGOT-6907: re-implement LivingEntity#setMaximumAir()
            if (maxAirTicks != getDefaultMaxAirSupply()) {
                output.putInt("Bukkit.MaxAirSupply", getMaxAirSupply());
            }
            // CraftBukkit end
            output.storeNullable("CustomName", ComponentSerialization.CODEC, this.getCustomName());
            if (this.isCustomNameVisible()) {
                output.putBoolean("CustomNameVisible", this.isCustomNameVisible());
            }

            if (this.isSilent()) {
                output.putBoolean("Silent", this.isSilent());
            }

            if (this.isNoGravity()) {
                output.putBoolean("NoGravity", this.isNoGravity());
            }

            if (this.hasGlowingTag) {
                output.putBoolean("Glowing", true);
            }

            int ticksFrozen = this.getTicksFrozen();
            if (ticksFrozen > 0) {
                output.putInt("TicksFrozen", this.getTicksFrozen());
            }

            if (this.hasVisualFire) {
                output.putBoolean("HasVisualFire", this.hasVisualFire);
            }

            if (!this.tags.isEmpty()) {
                output.store("Tags", TAG_LIST_CODEC, List.copyOf(this.tags));
            }

            if (!this.customData.isEmpty()) {
                output.store("data", CustomData.CODEC, this.customData);
            }

            this.addAdditionalSaveData(output, includeAll); // CraftBukkit - pass on includeAll
            if (this.isVehicle()) {
                ValueOutput.ValueOutputList passengersList = output.childrenList("Passengers");

                for(Entity passenger : this.getPassengers()) {
                    ValueOutput passengerOutput = passengersList.addChild();
                    if (!passenger.saveAsPassenger(passengerOutput, includeAll)) { // CraftBukkit - pass on includeAll
                        passengersList.discardLast();
                    }
                }

                if (passengersList.isEmpty()) {
                    output.discard("Passengers");
                }
            }

            // CraftBukkit start - stores eventually existing bukkit values
            if (this.bukkitEntity != null) {
                this.bukkitEntity.storeBukkitValues(output);
            }
            // CraftBukkit end
        } catch (Throwable t) {
            CrashReport report = CrashReport.forThrowable(t, "Saving entity NBT");
            CrashReportCategory category = report.addCategory("Entity being saved");
            this.fillCrashReportCategory(category);
            throw new ReportedException(report);
        }
    }

    @Override
    public boolean dropAllLeashConnections(@Nullable Player player, EntityUnleashEvent.UnleashReason reason) {
        List<Leashable> leashables = Leashable.leashableLeashedTo(((Entity) (Object) this));
        boolean dropped = !leashables.isEmpty();
        if (this instanceof Leashable leashableThis) {
            if (leashableThis.isLeashed()) {
                this.level().getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), reason)); // CraftBukkit
                leashableThis.dropLeash();
                dropped = true;
            }
        }

        for(Leashable leashable : leashables) {
            // CraftBukkit start
            if (leashable instanceof Entity entity) {
                this.level().getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(entity.getBukkitEntity(), reason));
            }
            // CraftBukkit end
            leashable.dropLeash();
        }

        if (dropped) {
            this.gameEvent(GameEvent.SHEAR, player);
            return true;
        } else {
            return false;
        }
    }

    // CraftBukkit start - collidable API
    @Override
    public boolean canCollideWithBukkit(Entity entity) {
        return isPushable();
    }
    // CraftBukkit end

    // CraftBukkit start - allow excluding certain data when saving
    protected void addAdditionalSaveData(ValueOutput output, boolean includeAll) {
        addAdditionalSaveData(output);
    }
    // CraftBukkit end

    // CraftBukkit start - Add delegate methods
    @Override
    public SoundEvent getSwimSound0() {
        return getSwimSound();
    }

    @Override
    public SoundEvent getSwimSplashSound0() {
        return getSwimSplashSound();
    }

    @Override
    public SoundEvent getSwimHighSpeedSplashSound0() {
        return getSwimHighSpeedSplashSound();
    }
    // CraftBukkit end

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
