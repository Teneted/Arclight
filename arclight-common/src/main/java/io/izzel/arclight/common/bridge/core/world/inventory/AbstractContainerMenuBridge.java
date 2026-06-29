package io.izzel.arclight.common.bridge.core.world.inventory;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.inventory.InventoryView;

public interface AbstractContainerMenuBridge {

    default InventoryView getBukkitView() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$transferTo(AbstractContainerMenu other, CraftHumanEntity player) {
        throw new IllegalStateException("Not implemented");
    }

    default Component bridge$getTitle() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setTitle(Component title) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isCheckReachable() {
        throw new IllegalStateException("Not implemented");
    }
}
