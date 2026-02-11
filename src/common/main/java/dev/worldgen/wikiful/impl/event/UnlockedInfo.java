package dev.worldgen.wikiful.impl.event;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public abstract class UnlockedInfo {
    public static final MapCodec<List<Identifier>> CODEC = Identifier.CODEC.listOf().fieldOf("entries");
    public static final StreamCodec<ByteBuf, List<Identifier>> STREAM_CODEC = Identifier.STREAM_CODEC.apply(ByteBufCodecs.list());

    public abstract void add(ServerPlayer player, Identifier id);
    public abstract void remove(ServerPlayer player, Identifier id);
    public abstract boolean hasUnlocked(Player player, Identifier id);
    public abstract void clear(ServerPlayer player);
}
