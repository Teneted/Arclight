package io.izzel.arclight.common.bridge.core.network.syncher;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerPlayer;

public interface SynchedEntityDataBridge {

    default <T> void markDirty(EntityDataAccessor<T> entitydataaccessor) {
        throw new IllegalStateException("Not implemented");
    }

    default void refresh(ServerPlayer player) {
        throw new IllegalStateException("Not implemented");
    }
}
