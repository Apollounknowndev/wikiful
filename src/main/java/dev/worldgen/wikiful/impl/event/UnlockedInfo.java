package dev.worldgen.wikiful.impl.event;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

//? if fabric {
import dev.worldgen.wikiful.impl.platform.fabric.WikifulAttachments;
//? } else {
/*import dev.worldgen.wikiful.impl.platform.neoforge.WikifulAttachments;
*///? }

public abstract class UnlockedInfo {
    public static final MapCodec<List<Identifier>> CODEC = Identifier.CODEC.listOf().fieldOf("entries");
    public static final StreamCodec<ByteBuf, List<Identifier>> STREAM_CODEC = Identifier.STREAM_CODEC.apply(ByteBufCodecs.list());
    
    private static <T extends TriggerHolder> List<Identifier> getUnlocks(ResourceKey<Registry<T>> key, Player player) {
        //? if fabric {
        if (key.equals(WikifulRegistries.TIP)) return player.getAttachedOrCreate(WikifulAttachments.UNLOCKED_TIPS);
        if (key.equals(WikifulRegistries.SECTION)) return player.getAttachedOrCreate(WikifulAttachments.UNLOCKED_SECTIONS);
        if (key.equals(WikifulRegistries.PAGE)) return player.getAttachedOrCreate(WikifulAttachments.UNLOCKED_PAGES);
        //? } else {
        /*if (key.equals(WikifulRegistries.TIP)) return player.getData(WikifulAttachments.UNLOCKED_TIPS);
        if (key.equals(WikifulRegistries.SECTION)) return player.getData(WikifulAttachments.UNLOCKED_SECTIONS);
        if (key.equals(WikifulRegistries.PAGE)) return player.getData(WikifulAttachments.UNLOCKED_PAGES);
        *///? }
        Wikiful.LOGGER.error("Trying to get unlocks for unknown registry: {}", key.identifier());
        return List.of();
    }
    
    private static <T extends TriggerHolder> void setUnlocks(ResourceKey<Registry<T>> key, Player player, List<Identifier> ids) {
        //? if fabric {
        if (key.equals(WikifulRegistries.TIP)) player.setAttached(WikifulAttachments.UNLOCKED_TIPS, ids);
        else if (key.equals(WikifulRegistries.SECTION)) player.setAttached(WikifulAttachments.UNLOCKED_SECTIONS, ids);
        else if (key.equals(WikifulRegistries.PAGE)) player.setAttached(WikifulAttachments.UNLOCKED_PAGES, ids);
        //? } else {
        /*if (key.equals(WikifulRegistries.TIP)) player.setData(WikifulAttachments.UNLOCKED_TIPS, ids);
        else if (key.equals(WikifulRegistries.SECTION)) player.setData(WikifulAttachments.UNLOCKED_SECTIONS, ids);
        else if (key.equals(WikifulRegistries.PAGE)) player.setData(WikifulAttachments.UNLOCKED_PAGES, ids);
        *///? }
        else Wikiful.LOGGER.error("Trying to set unlocks for unknown registry: {}", key.identifier());
    }
    
    public static <T extends TriggerHolder> void addUnlock(ResourceKey<Registry<T>> key, Player player, Identifier id) {
        List<Identifier> entries = new ArrayList<>(getUnlocks(key, player));
        entries.add(id);
        setUnlocks(key, player, entries);
    }
    
    public static <T extends TriggerHolder> void removeUnlock(ResourceKey<Registry<T>> key, Player player, Identifier id) {
        List<Identifier> entries = new ArrayList<>(getUnlocks(key, player));
        entries.remove(id);
        setUnlocks(key, player, entries);
    }
    
    public static <T extends TriggerHolder> boolean hasUnlocked(ResourceKey<Registry<T>> key, Player player, Identifier id) {
        return getUnlocks(key, player).contains(id);
    }
    
    public static <T extends TriggerHolder> void clearUnlocks(ResourceKey<Registry<T>> key, Player player) {
        setUnlocks(key, player, List.of());
    }
}
