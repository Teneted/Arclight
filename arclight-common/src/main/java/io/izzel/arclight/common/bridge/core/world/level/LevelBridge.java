package io.izzel.arclight.common.bridge.core.world.level;

import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gamerules.GameRules;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CapturedBlockState;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.generator.ChunkGenerator;
import org.spigotmc.SpigotWorldConfig;

import java.util.Map;

public interface LevelBridge extends LevelWriterBridge, LevelAccessorBridge {

    default boolean bridge$isCaptureBlockStates() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setCaptureBlockStates(boolean captureBlockStates) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isCaptureTreeGeneration() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setCaptureTreeGeneration(boolean captureTreeGeneration) {
        throw new IllegalStateException("Not implemented");
    }

    default Map<BlockPos, CapturedBlockState> bridge$getCapturedBlockStates() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setCapturedBlockStates(Map<BlockPos, CapturedBlockState> capturedBlockStates) {
        throw new IllegalStateException("Not implemented");
    }

    default CraftWorld getWorld() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isPopulating() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setPopulating(boolean populating) {
        throw new IllegalStateException("Not implemented");
    }

    default ChunkGenerator bridge$getGenerator() {
        throw new IllegalStateException("Not implemented");
    }

    default BlockEntity getBlockEntity(BlockPos pos, boolean validate) {
        throw new IllegalStateException("Not implemented");
    }

    default SpigotWorldConfig bridge$spigotConfig() {
        throw new IllegalStateException("Not implemented");
    }

    default Object2LongOpenHashMap<SpawnCategory> bridge$ticksPerSpawnCategory() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$setLastPhysicsProblem(BlockPos pos) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$preventPoiUpdated() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$preventPoiUpdated(boolean b) {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$forge$notifyAndUpdatePhysics(BlockPos pos, LevelChunk chunk, BlockState oldBlock, BlockState newBlock, int i, int j) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$forge$onBlockPlace(BlockPos pos, LivingEntity livingEntity, Direction direction) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$forge$mobGriefing(Entity entity) {
        if (this instanceof ServerLevel serverLevel) {
            return serverLevel.getGameRules().get(GameRules.MOB_GRIEFING);
        }
        return GameRules.MOB_GRIEFING.defaultValue();
    }

    default void bridge$forge$onPotionBrewed(NonNullList<ItemStack> stacks) {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$forge$restoringBlockSnapshots() {
        throw new IllegalStateException("Not implemented");
    }

    default Map<BlockPos, CapturedBlockState> bridge$getCapturedBlockState() {
        throw new IllegalStateException("Not implemented");
    }

    default Map<BlockPos, BlockEntity> bridge$getCapturedBlockEntity() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$platform$startCaptureBlockBreak() {
        throw new IllegalStateException("Not implemented");
    }

    default boolean bridge$isCapturingBlockBreak() {
        throw new IllegalStateException("Not implemented");
    }

    default void bridge$platform$endCaptureBlockBreak() {
        throw new IllegalStateException("Not implemented");
    }

    default CraftServer getCraftServer() {
        throw new IllegalStateException("Not implemented");
    }

    default ResourceKey<LevelStem> getTypeKey(){
        throw new IllegalStateException("Not implemented");
    }

    default void notifyAndUpdatePhysics(BlockPos blockpos, LevelChunk levelchunk, BlockState oldBlock, BlockState newBlock, BlockState actualBlock, int i, int j) {
        throw new IllegalStateException("Not implemented");
    }
}
