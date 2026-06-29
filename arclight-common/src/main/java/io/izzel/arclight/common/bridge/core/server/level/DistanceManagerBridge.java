package io.izzel.arclight.common.bridge.core.server.level;

import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;

public interface DistanceManagerBridge {

    default boolean bridge$addTicket(long chunkPos, Ticket ticket) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$removeTicket(long chunkPos, Ticket ticket) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$tick(ChunkMap chunkMap) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$removeAllTicketsFor(TicketType ticketType, int ticketLevel, Object ticketIdentifier) {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$offerUpdate(ChunkHolder holder) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$platform$isTicketForceTick(Ticket ticket) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$addForcedTicket(long chunkPosIn, Ticket ticketIn) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$removeForcedTicket(long chunkPosIn, Ticket ticketIn) {
        throw new IllegalStateException("Not implemented");
    }
}
