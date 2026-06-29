package io.izzel.arclight.common.bridge.core.world.level.chunk;

public interface LevelChunkBridge {

    default boolean bridge$isMustNotSave() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setMustNotSave(boolean mustNotSave) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isNeedsDecoration() {
        throw new IllegalStateException("Not implemented");
    }

    default void setUnsaved(boolean b) {
        throw new IllegalStateException("Not implemented");
    }

    default void loadCallback() {
        throw new IllegalStateException("Not implemented");
    }

    default void unloadCallback() {
        throw new IllegalStateException("Not implemented");
    }
}
