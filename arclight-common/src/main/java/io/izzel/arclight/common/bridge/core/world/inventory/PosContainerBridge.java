package io.izzel.arclight.common.bridge.core.world.inventory;

import net.minecraft.world.inventory.ContainerLevelAccess;
import org.bukkit.Location;

public interface PosContainerBridge extends AbstractContainerMenuBridge {

    default ContainerLevelAccess bridge$getWorldPos() {
        return null;
    }

    default Location bridge$getWorldLocation() {
        return bridge$getWorldPos().getLocation();
    }
}
