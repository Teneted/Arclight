package io.izzel.arclight.common.bridge.core.server.level;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.SavedDataStorage;
import net.minecraft.world.level.timers.TimerQueue;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import io.izzel.arclight.common.bridge.core.world.level.LevelBridge;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.jetbrains.annotations.Nullable;

public interface ServerLevelBridge extends LevelBridge {

    default LevelStorageSource.LevelStorageAccess bridge$getStorageSource() {
        throw new IllegalStateException("Not implemented");
    }

    default WorldGenSettings getWorldGenSettings() {
        throw new IllegalStateException("Not implemented");
    }

    default CustomBossEvents getCustomBossEvents() {
        throw new IllegalStateException("Not implemented");
    }

    default RandomSource getRandomSequence(Identifier key) {
        throw new IllegalStateException("Not implemented");
    }

    default RandomSequences getRandomSequences() {
        throw new IllegalStateException("Not implemented");
    }

    default void setWeatherParameters(int clearTime, int rainTime, boolean raining, boolean thundering) {
        throw new IllegalStateException("Not implemented");
    }

    default TimerQueue<MinecraftServer> getScheduledEvents() {
        throw new IllegalStateException("Not implemented");
    }

    default LevelChunk getChunkIfLoaded(int x, int z) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean addFreshEntity(Entity entity, CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean addWithUUID(Entity entity, CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default void addDuringTeleport(Entity entity, CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean tryAddFreshEntityWithPassengers(Entity entity, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason reason) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean strikeLightning(Entity entitylightning) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean strikeLightning(Entity entitylightning, LightningStrikeEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default ServerExplosion explode0(@Nullable Entity source, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator damageCalculator, double x, double y, double z, float r, boolean fire, Level.ExplosionInteraction interactionType, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, WeightedList<ExplosionParticleInfo> blockParticles, Holder<SoundEvent> explosionSound) {
        throw new IllegalStateException("Not implemented");
    }

    default <T extends ParticleOptions> int sendParticlesSource(ServerPlayer sender, T particle, boolean overrideLimiter, boolean alwaysShow, double x, double y, double z, int count, double xDist, double yDist, double zDist, double speed) {
        throw new IllegalStateException("Not implemented");
    }

    default SavedDataStorage getWorldContainerDataStorage() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushStrikeLightningCause(LightningStrikeEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

}
