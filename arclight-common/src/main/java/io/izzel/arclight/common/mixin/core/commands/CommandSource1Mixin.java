package io.izzel.arclight.common.mixin.core.commands;

import io.izzel.arclight.common.bridge.core.commands.CommandSource_1Bridge;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.command.NullCommandSender;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net/minecraft/commands/CommandSource$1")
public class CommandSource1Mixin implements CommandSource_1Bridge {

    @Override
    public CommandSender getBukkitSender(CommandSourceStack wrapper) {
        return NullCommandSender.INSTANCE;
    }
}
