package io.izzel.arclight.common.mixin.core.world.level.storage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Dynamic;
import io.izzel.arclight.common.bridge.core.world.level.storage.LevelStorageSourceBridge;
import io.izzel.arclight.common.mod.mixins.annotation.TransformAccess;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelDataAndDimensions;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.validation.ContentValidationException;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unchecked")
@Mixin(LevelStorageSource.class)
public abstract class LevelStorageSourceMixin implements LevelStorageSourceBridge {

    @Shadow
    public abstract LevelStorageSource.LevelStorageAccess validateAndCreateAccess(String levelId) throws IOException, ContentValidationException;

    @Shadow
    public abstract LevelStorageSource.LevelStorageAccess createAccess(String levelId) throws IOException;

    @Inject(method = "getLevelDataAndDimensions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelDataAndDimensions;create(Lnet/minecraft/world/level/storage/WorldData;Lnet/minecraft/world/level/levelgen/WorldGenSettings;Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;)Lnet/minecraft/world/level/storage/LevelDataAndDimensions;"))
    private static void arclight$putPDC(LevelStorageSource.LevelStorageAccess worldAccess, Dynamic<?> levelDataTag, WorldDataConfiguration dataConfiguration, Registry<LevelStem> datapackDimensions, HolderLookup.Provider registryAccess, CallbackInfoReturnable<LevelDataAndDimensions> cir, @Local PrimaryLevelData worldData, @Local(ordinal = 1) Dynamic<?> dataTag) {
        worldData.bridge$setPdc(((Dynamic<Tag>) dataTag).getElement("BukkitValues", null)); // CraftBukkit - Add PDC to world
    }

    @Unique
    private AtomicReference<ResourceKey<LevelStem>> arclight$dimensionType = new AtomicReference<>();

    @ModifyReturnValue(method = "validateAndCreateAccess", at = @At("RETURN"))
    private LevelStorageSource.LevelStorageAccess arclight$modifyDimType(LevelStorageSource.LevelStorageAccess original) {
        if (arclight$dimensionType != null) {
            original.bridge$setDimType(arclight$dimensionType.get());
        }
        arclight$dimensionType.set(null);
        return original;
    }

    @ModifyReturnValue(method = "createAccess", at = @At("RETURN"))
    private LevelStorageSource.LevelStorageAccess arclight$modifyDimType0(LevelStorageSource.LevelStorageAccess original) {
        if (arclight$dimensionType != null) {
            original.bridge$setDimType(arclight$dimensionType.get());
        }
        arclight$dimensionType.set(null);
        return original;
    }

    @Override
    public LevelStorageSource.LevelStorageAccess validateAndCreateAccess(String levelId, ResourceKey<LevelStem> dimensionType) throws IOException, ContentValidationException { // CraftBukkit
        arclight$dimensionType.set(dimensionType);
        return validateAndCreateAccess(levelId);
    }


    @Override
    public LevelStorageSource.LevelStorageAccess createAccess(final String levelId, ResourceKey<LevelStem> dimensionType) throws IOException { // CraftBukkit
        arclight$dimensionType.set(dimensionType);
        return createAccess(levelId);
    }

    // CraftBukkit start
    @TransformAccess(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
    private static Path getPre261StorageFolder(Path path, ResourceKey<LevelStem> dimensionType) {
        if (dimensionType == LevelStem.OVERWORLD) {
            return path;
        } else if (dimensionType == LevelStem.NETHER) {
            return path.resolve("DIM-1");
        } else if (dimensionType == LevelStem.END) {
            return path.resolve("DIM1");
        } else {
            return path.resolve("dimensions").resolve(dimensionType.identifier().getNamespace()).resolve(dimensionType.identifier().getPath());
        }
    }
    // CraftBukkit end
}
