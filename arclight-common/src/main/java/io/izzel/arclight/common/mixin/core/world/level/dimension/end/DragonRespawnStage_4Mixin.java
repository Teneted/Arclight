package io.izzel.arclight.common.mixin.core.world.level.dimension.end;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(targets = "net.minecraft.world.level.dimension.end.DragonRespawnStage$4")
public class DragonRespawnStage_4Mixin {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;discard()V"))
    private void arclight$pushDiscardReason(ServerLevel level, EnderDragonFight fight, List<EndCrystal> crystals, int time, CallbackInfo ci, @Local EndCrystal crystal) {
        crystal.bridge$pushEntityRemoveCause(EntityRemoveEvent.Cause.EXPLODE); // CraftBukkit - add Bukkit remove cause
    }
}
