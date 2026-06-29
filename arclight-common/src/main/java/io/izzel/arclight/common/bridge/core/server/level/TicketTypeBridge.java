package io.izzel.arclight.common.bridge.core.server.level;

public interface TicketTypeBridge {

    default long timeout() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setLifespan(long lifespan) {
        throw new IllegalStateException("Not implemented");
    }
}
