package io.izzel.arclight.common.bridge.core.server.players;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.net.SocketAddress;
import java.util.List;

public interface PlayerListBridge {

    default void bridge$setPlayers(List<ServerPlayer> players) {
        throw new IllegalStateException("Not implemented");
    }

    default List<ServerPlayer> bridge$getPlayers() {
        throw new IllegalStateException("Not implemented");
    }

    default CraftServer bridge$getCraftServer() {
        throw new IllegalStateException("Not implemented");
    }

    default ServerPlayer bridge$canPlayerLogin(SocketAddress socketAddress, GameProfile gameProfile, ServerLoginPacketListenerImpl handler) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$sendMessage(Component[] components) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushRespawnCause(PlayerRespawnEvent.RespawnReason respawnReason) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$platform$onTravelToDimension(Player player, ResourceKey<Level> dimension) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$platform$onPlayerChangedDimension(Player player, ResourceKey<Level> fromDim, ResourceKey<Level> toDim) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$platform$onPlayerRespawn(Player player, boolean endConquered) {
        throw new IllegalStateException("Not implemented");
    }
}
