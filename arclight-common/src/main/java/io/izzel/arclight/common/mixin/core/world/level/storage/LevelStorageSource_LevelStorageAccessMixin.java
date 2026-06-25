package io.izzel.arclight.common.mixin.core.world.level.storage;

import io.izzel.arclight.common.bridge.core.world.level.storage.LevelStorageSourceBridge;
import io.izzel.arclight.common.mod.mixins.annotation.CreateConstructor;
import io.izzel.arclight.common.mod.mixins.annotation.ShadowConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.IOException;
import java.nio.file.Path;

@Mixin(LevelStorageSource.LevelStorageAccess.class)
public class LevelStorageSource_LevelStorageAccessMixin implements LevelStorageSourceBridge.LevelStorageAccessBridge {

    // CraftBukkit start
    public ResourceKey<LevelStem> dimensionType;

    @ShadowConstructor
    public void arclight$constructor(String levelId, Path path) throws IOException {
        throw new RuntimeException();
    }

    @CreateConstructor
    public void arclight$constructor(String levelId, Path path, ResourceKey<LevelStem> dimensionType) throws IOException {
        this.arclight$constructor(levelId, path);
        this.dimensionType = dimensionType;
        // CraftBukkit end
    }

    @ModifyArg(method = "getDimensionPath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/DimensionType;getStorageFolder(Lnet/minecraft/resources/ResourceKey;Ljava/nio/file/Path;)Ljava/nio/file/Path;"), index = 0)
    private ResourceKey<Level> arclight$useLevelStem(ResourceKey<Level> name) {
        return this.dimensionType != null ? Registries.levelStemToLevel(this.dimensionType) : name;
    }

    @Override
    public void bridge$setDimType(ResourceKey<LevelStem> typeKey) {
        this.dimensionType = typeKey;
    }

    @Override
    public ResourceKey<LevelStem> bridge$getTypeKey() {
        return this.dimensionType;
    }
}
