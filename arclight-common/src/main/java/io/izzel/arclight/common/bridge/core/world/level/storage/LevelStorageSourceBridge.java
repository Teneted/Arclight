package io.izzel.arclight.common.bridge.core.world.level.storage;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.validation.ContentValidationException;

import java.io.IOException;

public interface LevelStorageSourceBridge {

    default LevelStorageSource.LevelStorageAccess createAccess(String saveName, ResourceKey<LevelStem> world) throws IOException {
        throw new IllegalStateException("Not implemented");
    }

    default LevelStorageSource.LevelStorageAccess validateAndCreateAccess(String saveName, ResourceKey<LevelStem> world) throws IOException, ContentValidationException {
        throw new IllegalStateException("Not implemented");
    }

    interface LevelStorageAccessBridge {

        default void bridge$setDimType(ResourceKey<LevelStem> typeKey) {
            throw new IllegalStateException("Not implemented");
        }

        default ResourceKey<LevelStem> bridge$getTypeKey() {
            throw new IllegalStateException("Not implemented");
        }
    }
}
