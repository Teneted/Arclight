package io.izzel.arclight.common.mixin.core.world.level.gamerules;

import io.izzel.arclight.common.bridge.core.world.level.gamerules.GameRulesBridge;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;
import java.util.Set;

@Mixin(GameRules.class)
public abstract class GameRulesMixin implements GameRulesBridge {

    @Shadow
    @Final
    public GameRuleMap rules;

    @Shadow
    @Final
    private static Logger LOGGER;

    @Override
    public <T> void set(final GameRule<T> gameRule, final T value, final @Nullable ServerLevel server) {
        if (!this.rules.has(gameRule)) {
            LOGGER.warn("Tried to set invalid game rule '{}' to value '{}'", gameRule.getIdentifierWithFallback(), value);
        } else {
            this.rules.set(gameRule, value);
            if (server != null) {
                server.getServer().onGameRuleChanged(gameRule, value, server);
            }
        }
    }

    @Override
    public void setAll(final GameRules other, final @Nullable ServerLevel server) {
        this.setAll(other.rules, server);
    }

    @Override
    public void setAll(GameRuleMap gameRulesMap, @Nullable ServerLevel server) { // CraftBukkit - per-world
        gameRulesMap.keySet().forEach((gamerule) -> {
            this.setFromOther(gameRulesMap, gamerule, server);
        });
    }

    private <T> void setFromOther(GameRuleMap gameRulesMap, GameRule<T> gameRule, @Nullable ServerLevel server) { // CraftBukkit - per-world
        this.set(gameRule, Objects.requireNonNull(gameRulesMap.get(gameRule)), server);
    }

    @Override
    public Set<GameRule<?>> arclight$getAllRules() {
        return rules.keySet();
    }
}
