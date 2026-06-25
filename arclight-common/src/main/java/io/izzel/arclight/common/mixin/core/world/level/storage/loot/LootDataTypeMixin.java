package io.izzel.arclight.common.mixin.core.world.level.storage.loot;

import com.mojang.serialization.Codec;
import io.izzel.arclight.common.bridge.core.world.level.storage.loot.LootDataTypeBridge;
import io.izzel.arclight.common.mod.mixins.annotation.CreateConstructor;
import io.izzel.arclight.common.mod.mixins.annotation.ShadowConstructor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;
import org.bukkit.craftbukkit.CraftLootTable;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@SuppressWarnings("unchecked")
@Mixin(LootDataType.class)
public class LootDataTypeMixin<T extends Validatable> implements LootDataTypeBridge<T> {

    @Mutable
    @Shadow
    @Final
    public static LootDataType<LootTable> TABLE;
    private BiConsumer<ResourceKey<T>, T> postValidate;

    @ShadowConstructor
    public void arclight$constructor(ResourceKey<Registry<T>> registryKey, Codec<T> codec, LootDataType.ContextGetter<T> contextGetter) {
        throw new RuntimeException();
    }

    @CreateConstructor
    public void arclight$constructor(ResourceKey<Registry<T>> registryKey, Codec<T> codec, LootDataType.ContextGetter<T> contextGetter, BiConsumer<ResourceKey<T>, T> postValidate) {
        this.arclight$constructor(registryKey, codec, contextGetter);
        this.postValidate = postValidate;
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void arclight$resetTables(CallbackInfo ci) {
        var lootDataType = new LootDataType<>(Registries.LOOT_TABLE, LootTable.DIRECT_CODEC, LootTable::getParamSet);
        lootDataType.bridge$setPostValidate((key, value) -> ((LootTable) value).bridge$setCraftLootTable(new CraftLootTable(CraftNamespacedKey.fromMinecraft(((ResourceKey<?>)key).identifier()), (LootTable) value)));
        TABLE = lootDataType;
    }

    @Inject(method = "runValidation(Lnet/minecraft/world/level/storage/loot/ValidationContextSource;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/storage/loot/Validatable;)V", at = @At("RETURN"))
    private void arclight$acceptPostValidate(ValidationContextSource contextSource, ResourceKey<T> key, T value, CallbackInfo ci) {
        this.postValidate.accept(key, value);
    }

    @Override
    public BiConsumer bridge$getPostValidate() {
        return this.postValidate;
    }

    @Override
    public void bridge$setPostValidate(BiConsumer postValidate) {
        this.postValidate = postValidate;
    }
}
