package io.izzel.arclight.common.mixin.core.world.level.gameevent.vibrations;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.CraftGameEvent;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VibrationSystem.Listener.class)
public class VibrationSystem_ListenerMixin {

    @ModifyExpressionValue(method = "handleGameEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem$User;canReceiveVibration(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Holder;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)Z"))
    private boolean arclight$callBlockReceiveGameEvent(boolean original, @Local(argsOnly = true) ServerLevel level, @Local(argsOnly = true) GameEvent.Context context, @Local(argsOnly = true) Holder<GameEvent> event, @Local(ordinal = 1) Vec3 destination) {
        // CraftBukkit start
        boolean defaultCancel = !original;
        Entity sourceEntity = context.sourceEntity();
        BlockReceiveGameEvent bukkitEvent = new BlockReceiveGameEvent(CraftGameEvent.minecraftToBukkit(event.value()), CraftBlock.at(level, BlockPos.containing(destination)), (sourceEntity == null) ? null : sourceEntity.getBukkitEntity());
        bukkitEvent.setCancelled(defaultCancel);
        level.getCraftServer().getPluginManager().callEvent(bukkitEvent);
        return bukkitEvent.isCancelled();
    }
}
