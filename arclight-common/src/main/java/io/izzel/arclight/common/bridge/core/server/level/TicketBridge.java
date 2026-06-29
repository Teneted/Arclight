package io.izzel.arclight.common.bridge.core.server.level;

public interface TicketBridge {

    default Object bridge$getKey() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setKey(Object key) {
        throw new IllegalStateException("Not implemented");
    }
}
