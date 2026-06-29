package io.izzel.arclight.common.bridge.core.server.players;

import java.util.Date;

public interface BanListEntryBridge {

    default Date getCreated() {
        throw new IllegalStateException("Not implemented");
    }
}
