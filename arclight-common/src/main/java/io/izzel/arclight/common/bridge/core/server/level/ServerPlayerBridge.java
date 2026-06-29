package io.izzel.arclight.common.bridge.core.server.level;

import com.mojang.datafixers.util.Either;
import io.izzel.arclight.common.bridge.core.world.entity.player.PlayerBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSpawnChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;

public interface ServerPlayerBridge extends PlayerBridge {

    default <L, R> Either<L, R> bridge$fireBedEvent(Either<L, R> e, BlockPos pos) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    default CraftPlayer getBukkitEntity() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushChangeDimensionCause(PlayerTeleportEvent.TeleportCause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushChangeSpawnCause(PlayerSpawnChangeEvent.Cause cause) {
        throw new IllegalStateException("Not implemented");
    }

    default Optional<PlayerTeleportEvent.TeleportCause> bridge$getTeleportCause() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushRespawnReason(PlayerRespawnEvent.RespawnReason respawnReason) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setTransferCookieConnection(CraftPlayer.TransferCookieConnection transferCookieConnection) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftPlayer.TransferCookieConnection bridge$getTransferCookieConnection() {
        throw new IllegalStateException("Not implemented");
    }

    default void resendItemInHands() {
        throw new IllegalStateException("Not implemented");
    }

    default BlockPos bridge$getSpawnPoint(ServerLevel world) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isMovementBlocked() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setCompassTarget(Location location) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isJoining() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$reset() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$initialized() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isTrackerDirty() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setTrackerDirty(boolean flag) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean arclight$isKeepLevel() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$readDeathEvent(PlayerDeathEvent event) {
        throw new IllegalStateException("Not implemented");
    }

    interface RespawnPosAngleBridge {

        default boolean bridge$isBedSpawn() {
            throw new IllegalStateException("Not implemented");
        }

        default boolean bridge$isAnchorSpawn() {
            throw new IllegalStateException("Not implemented");
        }

        default void bridge$setBedSpawn(boolean b) {
            throw new IllegalStateException("Not implemented");
        }

        default void bridge$setAnchorSpawn(boolean b) {
            throw new IllegalStateException("Not implemented");
        }
    }
}
