package io.izzel.arclight.common.bridge.core.network;

import com.mojang.authlib.properties.Property;

import java.net.SocketAddress;
import java.util.UUID;

public interface ConnectionBridge {

    default UUID bridge$getSpoofedUUID() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setSpoofedUUID(UUID spoofedUUID) {
        throw new IllegalStateException("Not implemented");
    }

    default Property[] bridge$getSpoofedProfile() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setSpoofedProfile(Property[] spoofedProfile) {
        throw new IllegalStateException("Not implemented");
    }

    default String bridge$getHostname() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setHostname(String hostname) {
        throw new IllegalStateException("Not implemented");
    }
}
