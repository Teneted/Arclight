package io.izzel.arclight.common.bridge.core.world.level.storage.loot;

import net.minecraft.world.Container;
import net.minecraft.world.level.storage.loot.LootParams;
import org.bukkit.craftbukkit.CraftLootTable;

public interface LootTableBridge {

    default void bridge$setCraftLootTable(CraftLootTable lootTable) {

    }

    default CraftLootTable bridge$getCraftLootTable() {
        return null;
    }

    default void fillInventory(Container container, LootParams params, long optionalRandomSeed, boolean plugin) {

    }
}
