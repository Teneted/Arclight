package io.izzel.arclight.common.mixin.core.commands;

import io.izzel.arclight.common.bridge.core.commands.CommandSourceBridge;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CommandSource.class)
public interface CommandSourceMixin extends CommandSourceBridge {

    @Override
    CommandSender getBukkitSender(CommandSourceStack wrapper); // CraftBukkit
}
