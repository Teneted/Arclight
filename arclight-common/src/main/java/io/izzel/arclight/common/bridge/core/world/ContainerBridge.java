package io.izzel.arclight.common.bridge.core.world;

import io.izzel.arclight.common.mod.util.WrappedContents;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public interface ContainerBridge {

    int MAX_STACK = 99;

    default List<ItemStack> getContents() {
        return new WrappedContents((Container) this);
    }

    default void onOpen(CraftHumanEntity who) {
        throw new IllegalStateException("Not implemented");
    }

    default void onClose(CraftHumanEntity who) {
        throw new IllegalStateException("Not implemented");
    }

    default List<HumanEntity> getViewers() {
        throw new IllegalStateException("Not implemented");
    }

    default InventoryHolder getOwner() {
        throw new IllegalStateException("Not implemented");
    }

    default void setOwner(InventoryHolder owner) {
        throw new IllegalStateException("Not implemented");
    }

    default void setMaxStackSize(int size) {
        throw new IllegalStateException("Not implemented");
    }

    default Location getLocation() {
        throw new IllegalStateException("Not implemented");
    }

    default RecipeHolder<?> getCurrentRecipe() {
        throw new IllegalStateException("Not implemented");
    }

    default void setCurrentRecipe(RecipeHolder<?> recipe) {
        throw new IllegalStateException("Not implemented");
    }

    default Inventory getOwnerInventory() {
        InventoryHolder owner = this.getOwner();
        if (owner != null) {
            return owner.getInventory();
        } else {
            return new CraftInventory((Container) this);
        }
    }
}
