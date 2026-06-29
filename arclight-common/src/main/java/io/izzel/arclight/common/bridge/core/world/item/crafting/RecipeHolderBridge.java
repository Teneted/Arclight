package io.izzel.arclight.common.bridge.core.world.item.crafting;

import org.bukkit.inventory.Recipe;

public interface RecipeHolderBridge {

    default Recipe bridge$toBukkitRecipe() {
        throw new IllegalStateException("Not implemented");
    }
}
