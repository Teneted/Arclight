package io.izzel.arclight.common.bridge.core.world.level.gamerules;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;

import java.util.Set;

public interface GameRulesBridge {

    default void setAll(final GameRules other, final @Nullable ServerLevel server) {
    }

    default void setAll(GameRuleMap gameRulesMap, @Nullable ServerLevel server) {
    }
    default <T> void set(final GameRule<T> gameRule, final T value, final @Nullable ServerLevel server) {
    }

    default Set<GameRule<?>> arclight$getAllRules() {
        return Set.of();
    }
}
