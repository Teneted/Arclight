package io.izzel.arclight.common.bridge.core.commands;

import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;

public interface CommandSource_1Bridge {

    default CommandSender getBukkitSender(CommandSourceStack wrapper) {
        return null;
    }
}
