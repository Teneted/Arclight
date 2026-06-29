package io.izzel.arclight.common.bridge.core.commands;

import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.permissions.PermissionSet;
import org.bukkit.command.CommandSender;

public interface CommandSourceStackBridge {

    default PermissionSet bridge$getBukkitPermissions() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setBukkitPermissions(PermissionSet bukkitPermissions) {
        throw new IllegalStateException("Not implemented");
    }

    default CommandNode<?> bridge$getCurrentCommand() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setCurrentCommand(CommandNode<?> node) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setSource(CommandSource source) {
        throw new IllegalStateException("Not implemented");
    }

    default CommandSender getBukkitSender() {
        throw new IllegalStateException("Not implemented");
    }
}
