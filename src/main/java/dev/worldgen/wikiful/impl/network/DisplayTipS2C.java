package dev.worldgen.wikiful.impl.network;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public record DisplayTipS2C(ResourceKey<Tip> id) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, DisplayTipS2C> CODEC = StreamCodec.composite(
        ResourceKey.streamCodec(WikifulRegistries.TIP),
        DisplayTipS2C::id,
        DisplayTipS2C::new
    );
    public static final Type<DisplayTipS2C> TYPE = new Type<>(Wikiful.id("display_tip"));
    
    public DisplayTipS2C(Identifier id) {
        this(ResourceKey.create(WikifulRegistries.TIP, id));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
