package io.izzel.arclight.common.mixin.core.world.level.saveddata.maps;

import io.izzel.arclight.common.bridge.core.world.level.saveddata.maps.MapItemSavedDataBridge;
import io.izzel.arclight.common.mod.mixins.annotation.CreateConstructor;
import io.izzel.arclight.common.mod.mixins.annotation.ShadowConstructor;
import io.izzel.arclight.common.mod.server.ArclightServer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapBanner;
import net.minecraft.world.level.saveddata.maps.MapFrame;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Mixin(MapItemSavedData.class)
public class MapItemSavedDataMixin implements MapItemSavedDataBridge {

    @Shadow
    @Final
    public List<MapItemSavedData.HoldingPlayer> carriedBy;

    @Shadow
    public ResourceKey<Level> dimension;
    // CraftBukkit start
    public CraftMapView mapView;
    public UUID uniqueId = null;
    public MapId id;

    @Inject(method = "<init>(IIBZZZLnet/minecraft/resources/ResourceKey;)V", at = @At("RETURN"))
    private void arclight$init(int centerX, int centerZ, byte scale, boolean trackingPosition, boolean unlimitedTracking, boolean locked, ResourceKey dimension, CallbackInfo ci) {
        // CraftBukkit start
        updateUUID();
        this.mapView = new CraftMapView(((MapItemSavedData) (Object) this));
    }

    @ShadowConstructor
    public void arclight$constructor(final int centerX, final int centerZ, final byte scale, final boolean trackingPosition, final boolean unlimitedTracking, final boolean locked, final ResourceKey<Level> dimension) {
        throw new RuntimeException();
    }

    @CreateConstructor
    public void arclight$constructor(ResourceKey<Level> dimension, int centerX, int centerZ, byte scale, ByteBuffer colors, boolean trackingPosition, boolean unlimitedTracking, boolean locked, List<MapBanner> banners, List<MapFrame> frames, long uuidLeast, long uuidMost) {
        this.arclight$constructor(centerX, centerZ, (byte) Mth.clamp(scale, 0, 4), trackingPosition, unlimitedTracking, locked, getWorldKey(dimension, uuidLeast, uuidMost));
    }

    private static ResourceKey<Level> getWorldKey(ResourceKey<Level> resourcekey, long uuidLeast, long uuidMost) {
        Level lookup = ArclightServer.getMinecraftServer().getLevel(resourcekey);
        if (lookup != null) {
            return resourcekey;
        }

        if (uuidLeast != 0L && uuidMost != 0L) {
            UUID uniqueId = new UUID(uuidMost, uuidLeast);

            CraftWorld world = (CraftWorld) Bukkit.getWorld(uniqueId);
            // Check if the stored world details are correct.
            if (world == null) {
                /* All Maps which do not have their valid world loaded are set to a dimension which hopefully won't be reached.
                   This is to prevent them being corrupted with the wrong map data. */
                // PAIL: Use Vanilla exception handling for now
            } else {
                return world.getHandle().dimension();
            }
        }
        throw new IllegalArgumentException("Invalid map dimension: " + resourcekey);
    }

    @Nullable
    private UUID updateUUID() {
        if (this.uniqueId == null) {
            Level level = ArclightServer.getMinecraftServer().getLevel(this.dimension);
            if (level != null) {
                this.uniqueId = level.getWorld().getUID();
            }
        }

        return this.uniqueId;
    }
    // CraftBukkit end

    @Override
    public CraftMapView bridge$getMapView() {
        return this.mapView;
    }

    @Override
    public MapId bridge$getId() {
        return this.id;
    }

    @Override
    public void bridge$setId(MapId id) {
        this.id = id;
    }

    @Override
    public List<MapItemSavedData.HoldingPlayer> bridge$getCarriedBy() {
        return this.carriedBy;
    }
}
