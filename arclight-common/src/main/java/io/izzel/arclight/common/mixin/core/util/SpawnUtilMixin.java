package io.izzel.arclight.common.mixin.core.util;

import com.llamalad7.mixinextras.sugar.Local;
import io.izzel.arclight.common.mod.mixins.annotation.TransformAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PostSpawnProcessor;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(SpawnUtil.class)
public class SpawnUtilMixin {

    @Shadow
    private static boolean moveToPossibleSpawnPosition(ServerLevel level, int spawnRangeY, BlockPos.MutableBlockPos searchPos, SpawnUtil.Strategy strategy) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(method = "trySpawnMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"))
    private static <T extends Mob> void arclight$pushSpawnCause(EntityType<T> entityType, EntitySpawnReason spawnReason, ServerLevel level, BlockPos start, int spawnAttempts, int spawnRangeXZ, int spawnRangeY, SpawnUtil.Strategy strategy, boolean checkCollisions, CallbackInfoReturnable<Optional<T>> cir, @Local T mob) {
        mob.arclight$pushAddEntityReason(CreatureSpawnEvent.SpawnReason.DEFAULT);
    }

    @Inject(method = "trySpawnMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;playAmbientSound()V"), cancellable = true)
    private static <T extends Mob> void arclight$returnIfRemoved(EntityType<T> entityType, EntitySpawnReason spawnReason, ServerLevel level, BlockPos start, int spawnAttempts, int spawnRangeXZ, int spawnRangeY, SpawnUtil.Strategy strategy, boolean checkCollisions, CallbackInfoReturnable<Optional<T>> cir, @Local T mob) {
        if (mob.isRemoved()) cir.setReturnValue(Optional.empty()); ; // CraftBukkit
    }

    @Inject(method = "trySpawnMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;discard()V"))
    private static <T extends Mob> void arclight$discardReason(EntityType<T> entityType, EntitySpawnReason spawnReason, ServerLevel level, BlockPos start, int spawnAttempts, int spawnRangeXZ, int spawnRangeY, SpawnUtil.Strategy strategy, boolean checkCollisions, CallbackInfoReturnable<Optional<T>> cir, @Local T mob) {
        mob.bridge$pushEntityRemoveCause(null);
    }

    @TransformAccess(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
    private static <T extends Mob> Optional<T> trySpawnMob(final EntityType<T> entityType, final EntitySpawnReason spawnReason, final ServerLevel level, final BlockPos start, final int spawnAttempts, final int spawnRangeXZ, final int spawnRangeY, final SpawnUtil.Strategy strategy, final boolean checkCollisions, CreatureSpawnEvent.SpawnReason reason) {
        BlockPos.MutableBlockPos searchPos = start.mutable();
        RandomSource random = level.getRandom();

        for(int i = 0; i < spawnAttempts; ++i) {
            int dx = Mth.randomBetweenInclusive(random, -spawnRangeXZ, spawnRangeXZ);
            int dz = Mth.randomBetweenInclusive(random, -spawnRangeXZ, spawnRangeXZ);
            searchPos.setWithOffset(start, dx, spawnRangeY, dz);
            if (level.getWorldBorder().isWithinBounds(searchPos) && moveToPossibleSpawnPosition(level, spawnRangeY, searchPos, strategy) && (!checkCollisions || level.noCollision(entityType.getSpawnAABB((double)searchPos.getX() + (double)0.5F, (double)searchPos.getY(), (double)searchPos.getZ() + (double)0.5F)))) {
                T mob = (T)(entityType.create(level, (PostSpawnProcessor)null, searchPos, spawnReason, false, false));
                if (mob != null) {
                    if (mob.checkSpawnRules(level, spawnReason) && mob.checkSpawnObstruction(level)) {
                        level.addFreshEntityWithPassengers(mob, reason); // CraftBukkit
                        if (mob.isRemoved()) return Optional.empty(); // CraftBukkit
                        mob.playAmbientSound();
                        return Optional.of(mob);
                    }

                    mob.discard(null); // CraftBukkit - add Bukkit remove cause
                }
            }
        }

        return Optional.empty();
    }
}
