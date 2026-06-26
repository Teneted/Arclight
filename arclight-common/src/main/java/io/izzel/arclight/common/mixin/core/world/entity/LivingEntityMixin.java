package io.izzel.arclight.common.mixin.core.world.entity;

import io.izzel.arclight.common.bridge.core.world.entity.LivingEntityBridge;
import io.izzel.arclight.common.bridge.vanilla.world.entity.LivingEntityBridge_Vanilla;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityBridge, LivingEntityBridge_Vanilla {

    // CraftBukkit start
    public int expToDrop;
    public ArrayList<ItemStack> drops = new ArrayList<org.bukkit.inventory.ItemStack>();
    public org.bukkit.craftbukkit.attribute.CraftAttributeMap craftAttributes;
    public boolean collides = true;
    public Set<UUID> collidableExemptions = new HashSet<>();
    public boolean bukkitPickUpLoot;
    public int invulnerableDuration = 20;

    @Override
    public ArrayList<ItemStack> bridge$getDrops() {
        return this.drops;
    }
}
