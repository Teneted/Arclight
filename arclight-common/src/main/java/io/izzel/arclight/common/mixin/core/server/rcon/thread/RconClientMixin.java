package io.izzel.arclight.common.mixin.core.server.rcon.thread;

import net.minecraft.server.ServerInterface;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.rcon.RconConsoleSource;
import net.minecraft.server.rcon.thread.RconClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Socket;

@Mixin(RconClient.class)
public class RconClientMixin {

    @Mutable @Shadow @Final private ServerInterface serverInterface;

    private RconConsoleSource rconConsoleSource;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void arclight$init(ServerInterface serverInterface, String rconPassword, Socket socket, CallbackInfo ci) {
        this.serverInterface = (DedicatedServer) serverInterface; // CraftBukkit
        var source =  new net.minecraft.server.rcon.RconConsoleSource((DedicatedServer) this.serverInterface); // CraftBukkit
        source.bridge$setSocketAddress(socket.getRemoteSocketAddress());
        this.rconConsoleSource = source;
    }

    @ModifyArg(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/rcon/thread/RconClient;sendCmdResponse(ILjava/lang/String;)V", ordinal = 0), index = 1)
    private String arlight$resetCmd(String response) {
        return ((DedicatedServer)this.serverInterface).runCommand(this.rconConsoleSource, response);
    }
}
