package io.izzel.arclight.common.mixin.core.world.level.saveddata;

import io.izzel.arclight.common.bridge.core.world.level.saveddata.WeatherDataBridge;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.WeatherData;
import org.bukkit.Bukkit;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherData.class)
public class WeatherDataMixin implements WeatherDataBridge {

    @Shadow
    private boolean thundering;
    @Shadow
    private boolean raining;
    // CraftBukkit start
    private ServerLevel world;

    @Override
    public void setWorld(ServerLevel world) {
        this.world = world;
    }
    // CraftBukkit end

    @Override
    public ServerLevel bridge$getWorld() {
        return this.world;
    }

    @Inject(method = "setThundering", at = @At("HEAD"), cancellable = true)
    private void arclight$callThunderChangeEvent(boolean thundering, CallbackInfo ci) {
        // CraftBukkit start
        if (this.thundering == thundering) {
            ci.cancel();
            return;
        }

        org.bukkit.World world = this.world.getWorld();
        if (world != null) {
            ThunderChangeEvent thunder = new ThunderChangeEvent(world, thundering);
            Bukkit.getServer().getPluginManager().callEvent(thunder);
            if (thunder.isCancelled()) {
                ci.cancel();
                return;
            }
        }
        // CraftBukkit end
    }

    @Inject(method = "setRaining", at = @At("HEAD"), cancellable = true)
    private void arclight$callWeatherChangeEvent(boolean raining, CallbackInfo ci) {
        // CraftBukkit start
        if (this.raining == raining) {
            ci.cancel();
            return;
        }

        org.bukkit.World world = this.world.getWorld();
        if (world != null) {
            WeatherChangeEvent weather = new WeatherChangeEvent(world, raining);
            Bukkit.getServer().getPluginManager().callEvent(weather);
            if (weather.isCancelled()) {
                ci.cancel();
                return;
            }
        }
        // CraftBukkit end
    }
}
