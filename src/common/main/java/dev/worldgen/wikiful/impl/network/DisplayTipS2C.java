package dev.worldgen.wikiful.impl.network;

import dev.worldgen.wikiful.impl.Wikiful;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DisplayTipS2C(ResourceLocation id) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, DisplayTipS2C> CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC,
        DisplayTipS2C::id,
        DisplayTipS2C::new
    );
    public static final Type<DisplayTipS2C> TYPE = new Type<>(Wikiful.id("display_tip"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
