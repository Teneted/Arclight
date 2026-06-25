package io.izzel.arclight.common.mixin.core.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResultContainer.class)
public abstract class ResultContainerMixin implements Container, RecipeCraftingHolder {

    @Shadow
    @Final
    private NonNullList<ItemStack> itemStacks;
    // CraftBukkit start
    private int maxStack = MAX_STACK;

    @Override
    public java.util.List<ItemStack> getContents() {
        return this.itemStacks;
    }

    @Override
    public org.bukkit.inventory.InventoryHolder getOwner() {
        return null; // Result slots don't get an owner
    }

    // Don't need a transaction; the InventoryCrafting keeps track of it for us
    @Override
    public void onOpen(CraftHumanEntity who) {}

    @Override
    public void onClose(CraftHumanEntity who) {}

    @Override
    public java.util.List<HumanEntity> getViewers() {
        return new java.util.ArrayList<HumanEntity>();
    }

    @Override
    public int getMaxStackSize() {
        return maxStack;
    }

    @Override
    public void setMaxStackSize(int size) {
        maxStack = size;
    }

    @Override
    public Location getLocation() {
        return null;
    }
    // CraftBukkit end
}
