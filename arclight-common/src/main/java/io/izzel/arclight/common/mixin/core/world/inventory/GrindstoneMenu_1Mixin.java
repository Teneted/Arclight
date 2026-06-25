package io.izzel.arclight.common.mixin.core.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import org.bukkit.Location;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$1")
public abstract class GrindstoneMenu_1Mixin implements Container, StackedContentsCompatible {

    @Shadow
    @Final
    GrindstoneMenu this$0;

    // CraftBukkit start
    @Override
    public Location getLocation() {
        return this$0.bridge$getWorldLocation();
    }
    // CraftBukkit end
}
