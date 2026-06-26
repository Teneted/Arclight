package io.izzel.arclight.common.mixin.core.server.gui;

import net.minecraft.server.gui.MinecraftServerGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MinecraftServerGui.class)
public class MinecraftServerGuiMixin {

    private static final java.util.regex.Pattern ANSI = java.util.regex.Pattern.compile("\\x1B\\[([0-9]{1,2}(;[0-9]{1,2})*)?[m|K]"); // CraftBukkit

    @ModifyArg(method = "print", at = @At(value = "INVOKE", target = "Ljavax/swing/text/Document;insertString(ILjava/lang/String;Ljavax/swing/text/AttributeSet;)V"), index = 1)
    private String arclight$useAnsi(String str) {
        return ANSI.matcher(str).replaceAll("");
    }
}
