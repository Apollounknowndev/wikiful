package dev.worldgen.wikiful.impl.event;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public abstract class UnlockedInfo {
    public static final Codec<List<ResourceLocation>> CODEC = ResourceLocation.CODEC.listOf();
    public static final StreamCodec<ByteBuf, List<ResourceLocation>> STREAM_CODEC = ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list());

    public abstract void add(ServerPlayer player, ResourceLocation id);
    public abstract void remove(ServerPlayer player, ResourceLocation id);
    public abstract boolean hasUnlocked(Player player, ResourceLocation id);
    public abstract void clear(ServerPlayer player);
}
