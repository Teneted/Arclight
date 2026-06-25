package io.izzel.arclight.common.mixin.core.world.level.gameevent;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftGameEvent;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.event.world.GenericGameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameEventDispatcher.class)
public class GameEventDispatcherMixin {

    @Shadow
    @Final
    private ServerLevel level;

    @Inject(method = "post", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/SectionPos;blockToSectionCoord(I)I", ordinal = 0), cancellable = true)
    private void arclight$callGenericGameEvent(Holder<GameEvent> gameEvent, Vec3 position, GameEvent.Context context, CallbackInfo ci, @Local BlockPos center, @Local(ordinal = 0) int radius) {
        // CraftBukkit start
        GenericGameEvent event = new GenericGameEvent(CraftGameEvent.minecraftToBukkit(gameEvent.value()), CraftLocation.toBukkit(center, this.level.getWorld()), (context.sourceEntity() == null) ? null : context.sourceEntity().getBukkitEntity(), radius, !Bukkit.isPrimaryThread());
        this.level.getCraftServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
            return;
        }
        radius = event.getRadius();
        // CraftBukkit end
    }
}
