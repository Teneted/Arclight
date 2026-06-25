package io.izzel.arclight.common.mixin.core.world.level.saveddata.maps;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.bukkit.craftbukkit.map.CraftMapCursor;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;


@Mixin(MapItemSavedData.HoldingPlayer.class)
public abstract class MapItemSavedData_HoldingPlayerMixin {

    @Shadow
    private int minDirtyX;

    @Shadow
    private int minDirtyY;

    @Shadow
    private int maxDirtyX;

    @Shadow
    private int maxDirtyY;

    @Shadow
    private boolean dirtyData;

    @Shadow
    @Final
    public Player player;

    @Shadow
    @Final
    MapItemSavedData this$0;

    @Shadow
    private boolean dirtyDecorations;

    @Shadow
    private int tick;

    private MapItemSavedData.MapPatch createPatch(byte[] buffer) { // CraftBukkit
        int startX = this.minDirtyX;
        int startY = this.minDirtyY;
        int width = this.maxDirtyX + 1 - this.minDirtyX;
        int height = this.maxDirtyY + 1 - this.minDirtyY;
        byte[] patch = new byte[width * height];

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                patch[x + y * width] = buffer[startX + x + (startY + y) * 128];
            }
        }

        return new MapItemSavedData.MapPatch(startX, startY, width, height, patch);
    }

    /**
     * @author wdog5734
     * @reason Bukkit
     */
    @Overwrite
    private @Nullable Packet<?> nextUpdatePacket(final MapId id) {
        MapItemSavedData.MapPatch patch;
        org.bukkit.craftbukkit.map.RenderData render = this$0.bridge$getMapView().render((org.bukkit.craftbukkit.entity.CraftPlayer) this.player.getBukkitEntity()); // CraftBukkit
        if (this.dirtyData) {
            this.dirtyData = false;
            patch = this.createPatch(render.buffer); // CraftBukkit
        } else {
            patch = null;
        }

        Collection<MapDecoration> decorations;
        if ((true || this.dirtyDecorations) && this.tick++ % 5 == 0) { // CraftBukkit - custom maps don't update this yet
            this.dirtyDecorations = false;
            // CraftBukkit start
            java.util.Collection<MapDecoration> icons = new java.util.ArrayList<MapDecoration>();

            for (org.bukkit.map.MapCursor cursor : render.cursors) {
                if (cursor.isVisible()) {
                    icons.add(new MapDecoration(CraftMapCursor.CraftType.bukkitToMinecraftHolder(cursor.getType()), cursor.getX(), cursor.getY(), cursor.getDirection(), CraftChatMessage.fromStringOrOptional(cursor.getCaption())));
                }
            }
            decorations = icons;
            // CraftBukkit end
        } else {
            decorations = null;
        }

        return decorations == null && patch == null ? null : new ClientboundMapItemDataPacket(id, this$0.scale, this$0.locked, decorations, patch);
    }
}