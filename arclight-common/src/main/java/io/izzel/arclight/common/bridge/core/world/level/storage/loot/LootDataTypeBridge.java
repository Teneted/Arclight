package io.izzel.arclight.common.bridge.core.world.level.storage.loot;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.Validatable;

import java.util.function.BiConsumer;

public interface LootDataTypeBridge<T extends Validatable> {

    default BiConsumer<ResourceKey<T>, T> bridge$getPostValidate() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPostValidate(BiConsumer<ResourceKey<T>, T> postValidate) {
        throw new IllegalStateException("Not implemented");
    }
}
