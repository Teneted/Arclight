package io.izzel.arclight.common.bridge.core.server.network;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public interface ServerCommonPacketListenerImplBridge {

    default boolean bridge$processedDisconnect() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isDisconnected() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$disconnect(String s) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftServer bridge$getCraftServer() {
        throw new IllegalStateException("Not implemented");
    }

    default CraftPlayer bridge$getCraftPlayer() {
        throw new IllegalStateException("Not implemented");
    }

    default ServerPlayer bridge$getPlayer() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPlayer(ServerPlayer player) {
        throw new IllegalStateException("Not implemented");
    }

}
