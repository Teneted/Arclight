package io.izzel.arclight.common.bridge.core.world.level.storage.loot;

import net.minecraft.world.Container;
import net.minecraft.world.level.storage.loot.LootParams;
import org.bukkit.craftbukkit.CraftLootTable;

public interface LootTableBridge {

    default void bridge$setCraftLootTable(CraftLootTable lootTable) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftLootTable bridge$getCraftLootTable() {
        throw new IllegalStateException("Not implemented");
    }

    default void fillInventory(Container container, LootParams params, long optionalRandomSeed, boolean plugin) {
        throw new IllegalStateException("Not implemented");
    }
}
