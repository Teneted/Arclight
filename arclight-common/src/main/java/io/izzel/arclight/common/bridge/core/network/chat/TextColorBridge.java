package io.izzel.arclight.common.bridge.core.network.chat;

import net.minecraft.ChatFormatting;

public interface TextColorBridge {

    default ChatFormatting bridge$getFormat(){
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setFormat(ChatFormatting format) {
        throw new IllegalStateException("Not implemented");
    }
}
