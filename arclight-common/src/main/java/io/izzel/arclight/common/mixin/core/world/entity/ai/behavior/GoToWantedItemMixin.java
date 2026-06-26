package io.izzel.arclight.common.mixin.core.world.entity.ai.behavior;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(GoToWantedItem.class)
public class GoToWantedItemMixin {

    @Inject(method = "lambda$create$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/memory/WalkTarget;<init>(Lnet/minecraft/world/entity/ai/behavior/PositionTracker;FI)V"))
    private static void arclight$callEntityTargetEvent(BehaviorBuilder.Instance i, MemoryAccessor wantedItem, MemoryAccessor cooldown, Predicate predicate, int maxDistToWalk, float speedModifier, MemoryAccessor lookTarget, MemoryAccessor walkTarget, ServerLevel level, LivingEntity body, long timestamp, CallbackInfoReturnable<Boolean> cir, @Local ItemEntity item) {
        // CraftBukkit start
        if (body instanceof net.minecraft.world.entity.animal.allay.Allay) {
            org.bukkit.event.entity.EntityTargetEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetEvent(body, item, org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_ENTITY);

            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }
            if (!(event.getTarget() instanceof ItemEntity)) {
                walkTarget.erase();
            }

            item = (ItemEntity) ((org.bukkit.craftbukkit.entity.CraftEntity) event.getTarget()).getHandle();
        }
        // CraftBukkit end
    }
}
