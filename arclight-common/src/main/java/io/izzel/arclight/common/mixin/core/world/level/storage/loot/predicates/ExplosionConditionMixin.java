package io.izzel.arclight.common.mixin.core.world.level.storage.loot.predicates;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ExplosionCondition.class)
public class ExplosionConditionMixin {

    /**
     * @author wdog5734
     * @reason Bukkit
     */
    @Overwrite
    public boolean test(final LootContext context) {
        Float explosionRadius = context.getOptionalParameter(LootContextParams.EXPLOSION_RADIUS);
        if (explosionRadius != null) {
            RandomSource random = context.getRandom();
            float probability = 1.0F / explosionRadius;
            // CraftBukkit - <= to < to allow for plugins to completely disable block drops from explosions
            return random.nextFloat() < probability;
        } else {
            return true;
        }
    }
}
