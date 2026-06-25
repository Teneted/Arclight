package io.izzel.arclight.common.mixin.core.world.level.storage;

import io.izzel.arclight.common.bridge.core.world.level.storage.PlayerDataStorageBridge;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.util.Optional;

@Mixin(PlayerDataStorage.class)
public abstract class PlayerDataStorageMixin implements PlayerDataStorageBridge {

    @Shadow
    @Final
    private File playerDir;

    @Shadow
    public abstract Optional<CompoundTag> load(NameAndId nameAndId);

    // CraftBukkit start
    @Override
    public Optional<CompoundTag> load(Player player) {
        return load(player.nameAndId()).map((compoundtag) -> {
            if (player instanceof ServerPlayer) {
                CraftPlayer craftPlayer = (CraftPlayer) player.getBukkitEntity();
                // Only update first played if it is older than the one we have
                long modified = new File(this.playerDir, player.getStringUUID() + ".dat").lastModified();
                if (modified < craftPlayer.getFirstPlayed()) {
                    craftPlayer.setFirstPlayed(modified);
                }
            }

            return compoundtag;
        });
    }
    // CraftBukkit end

    // CraftBukkit start
    @Override
    public File getPlayerDir() {
        return playerDir;
    }
    // CraftBukkit end
}
