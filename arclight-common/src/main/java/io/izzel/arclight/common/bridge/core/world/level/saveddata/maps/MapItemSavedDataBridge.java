package io.izzel.arclight.common.bridge.core.world.level.saveddata.maps;

import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.bukkit.craftbukkit.map.CraftMapView;

import java.util.List;

public interface MapItemSavedDataBridge {

    default CraftMapView bridge$getMapView() {
        throw new IllegalStateException("Not implemented");
    }

    default MapId bridge$getId() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setId(MapId id) {
        throw new IllegalStateException("Not implemented");
    }

    default List<MapItemSavedData.HoldingPlayer> bridge$getCarriedBy() {
        throw new IllegalStateException("Not implemented");
    }
}
