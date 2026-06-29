package io.izzel.arclight.common.bridge.core.network.protocol.common.custom;

import io.netty.buffer.ByteBuf;

public interface DiscardedPayloadBridge {

    default ByteBuf data() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$pushData(ByteBuf buf) {
        throw new IllegalStateException("Not implemented");
    }
}
