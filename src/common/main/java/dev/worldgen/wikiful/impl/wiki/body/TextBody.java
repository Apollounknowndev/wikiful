package dev.worldgen.wikiful.impl.wiki.body;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

public record TextBody(Component contents) implements Body {
    public static final MapCodec<TextBody> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ComponentSerialization.CODEC.fieldOf("contents").forGetter(TextBody::contents)
    ).apply(instance, TextBody::new));

    @Override
    public MapCodec<? extends Body> codec() {
        return CODEC;
    }
}
