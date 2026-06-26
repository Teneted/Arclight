package io.izzel.arclight.common.bridge.core.server.rcon;

import java.net.SocketAddress;

public interface RconConsoleSourceBridge {

    default void sendMessage(String message) {

    }

    default void bridge$setSocketAddress(SocketAddress socketAddress) {
    }
}
