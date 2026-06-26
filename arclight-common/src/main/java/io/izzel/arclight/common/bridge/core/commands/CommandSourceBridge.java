package io.izzel.arclight.common.bridge.core.commands;

import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;

public interface CommandSourceBridge {

    /*
     * Offer a way to recognize whether we have implemented a proper getBukkitSender
     * for the specified CommandSource.
     */
    default CommandSender getBukkitSender(CommandSourceStack wrapper) {
        return null;
    }
}
