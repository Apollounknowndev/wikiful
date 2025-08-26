package dev.worldgen.wikiful.impl.wiki.body;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.ExtraCodecs;

public record HorizontalLineBody(int height, int spacing, ColorRGBA color) implements Body {
    public static final MapCodec<HorizontalLineBody> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ExtraCodecs.POSITIVE_INT.fieldOf("height").orElse(1).forGetter(HorizontalLineBody::height),
        ExtraCodecs.NON_NEGATIVE_INT.fieldOf("spacing").orElse(0).forGetter(HorizontalLineBody::spacing),
        ColorRGBA.CODEC.fieldOf("color").orElse(new ColorRGBA(0xFFFFFFFF)).forGetter(HorizontalLineBody::color)
    ).apply(instance, HorizontalLineBody::new));

    @Override
    public MapCodec<? extends Body> codec() {
        return CODEC;
    }
}
