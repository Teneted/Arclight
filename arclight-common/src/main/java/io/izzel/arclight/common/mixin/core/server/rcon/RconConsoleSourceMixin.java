package io.izzel.arclight.common.mixin.core.server.rcon;

import io.izzel.arclight.common.bridge.core.server.rcon.RconConsoleSourceBridge;
import io.izzel.arclight.common.mod.mixins.annotation.CreateConstructor;
import io.izzel.arclight.common.mod.mixins.annotation.ShadowConstructor;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.rcon.RconConsoleSource;
import org.bukkit.craftbukkit.command.CraftRemoteConsoleCommandSender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.net.SocketAddress;

@Mixin(RconConsoleSource.class)
public abstract class RconConsoleSourceMixin implements CommandSource, RconConsoleSourceBridge {

    @Shadow
    @Final
    private StringBuffer buffer;
    // CraftBukkit start
    public SocketAddress socketAddress;
    private CraftRemoteConsoleCommandSender remoteConsole = new CraftRemoteConsoleCommandSender(((RconConsoleSource) (Object) this));

    @ShadowConstructor
    public void arclight$constructor(MinecraftServer server) {
        throw new RuntimeException();
    }

    @CreateConstructor
    public void arclight$constructor(MinecraftServer server, SocketAddress socketAddress) {
        arclight$constructor(server);
        this.socketAddress = socketAddress;
    }

    // CraftBukkit start - Send a String
    @Override
    public void sendMessage(String message) {
        this.buffer.append(message);
    }

    @Override
    public org.bukkit.command.CommandSender getBukkitSender(CommandSourceStack wrapper) {
        return this.remoteConsole;
    }
    // CraftBukkit end

    @Override
    public void bridge$setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}
