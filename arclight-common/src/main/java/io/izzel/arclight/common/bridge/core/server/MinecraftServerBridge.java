package io.izzel.arclight.common.bridge.core.server;

import joptsimple.OptionSet;
import net.minecraft.commands.Commands;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeSource;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.craftbukkit.CraftServer;

public interface MinecraftServerBridge {

    default <T> void onGameRuleChanged(final GameRule<T> rule, final T value, ServerLevel serverLevel) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setConsole(ConsoleCommandSender console) {
        throw new IllegalStateException("Not implemented");
    }

    default ConsoleCommandSender bridge$getConsole() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setServer(CraftServer server) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftServer bridge$getServer() {
        throw new IllegalStateException("Not implemented");
    }

    default RemoteConsoleCommandSender bridge$getRemoteConsole() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$queuedProcess(Runnable runnable) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$drainQueuedTasks() {
        throw new IllegalStateException("Not implemented");
    }

    default Commands bridge$getVanillaCommands() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$onServerLoad(ServerLevel level) {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$onServerUnload(ServerLevel level) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$markLevelsDirty() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$reinstatePersistentChunks(ServerLevel level) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$lockRegistries() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$unlockRegistries() {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$extendNextTickTimeTo(TimeSource.NanoTimeSource timeSource) {
        throw new IllegalStateException("Not implemented");
    }

    default WorldLoader.DataLoadContext bridge$getWorldLoader() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setWorldLoader(WorldLoader.DataLoadContext worldLoader) {
        throw new IllegalStateException("Not implemented");
    }

    default OptionSet bridge$getOptions() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setOptions(OptionSet options) {
        throw new IllegalStateException("Not implemented");
    }

    default java.util.Queue<Runnable> bridge$getProcessQueue() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setProcessQueue(java.util.Queue<Runnable> processQueue) {
        throw new IllegalStateException("Not implemented");
    }

    default int bridge$getAutosavePeriod() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setAutosavePeriod(int autosavePeriod) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setForceTicks(boolean forceTicks) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isForceTicks() {
        throw new IllegalStateException("Not implemented");
    }

    default Commands bridge$getVanillaCommandDispatcher() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setVanillaCommandDispatcher(Commands vanillaCommandDispatcher) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean hasStopped() {
        throw new IllegalStateException("Not implemented");
    }

    default void addLevel(ServerLevel level) {
        throw new IllegalStateException("Not implemented");
    }

    default void removeLevel(ServerLevel level) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean isDebugging() {
        throw new IllegalStateException("Not implemented");
    }

    default java.util.concurrent.ExecutorService bridge$getChatExecutor() {
        throw new IllegalStateException("Not implemented");
    }

    default ServerLevel findRespawnDimension(ServerLevel world) {
        throw new IllegalStateException("Not implemented");
    }

    default void setRespawnData(LevelData.RespawnData respawnData, ServerLevel world) {
        throw new IllegalStateException("Not implemented");
    }

    default void arclight$tickSpigotWatchdogInternal() {
        throw new IllegalStateException("Not implemented");
    }

    default void initWorld(ServerLevel serverWorld, ServerLevelData worldInfo, WorldData saveData, WorldOptions worldOptions) {
        throw new IllegalStateException("Not implemented");
    }

    default void prepareLevels(ServerLevel serverWorld) {
        throw new IllegalStateException("Not implemented");
    }
}
