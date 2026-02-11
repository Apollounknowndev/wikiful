package dev.worldgen.wikiful.impl.wiki.body;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public record SpriteBody(Identifier sprite, int textureWidth, int textureHeight, float scale, boolean centered) implements Body {
    public static final MapCodec<SpriteBody> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Identifier.CODEC.fieldOf("sprite").forGetter(SpriteBody::sprite),
        ExtraCodecs.POSITIVE_INT.fieldOf("texture_width").forGetter(SpriteBody::textureWidth),
        ExtraCodecs.POSITIVE_INT.fieldOf("texture_height").forGetter(SpriteBody::textureHeight),
        Codec.floatRange(0.001f, 1f).fieldOf("scale").orElse(0.5f).forGetter(SpriteBody::scale),
        Codec.BOOL.fieldOf("centered").orElse(true).forGetter(SpriteBody::centered)
    ).apply(instance, SpriteBody::new));

    @Override
    public MapCodec<? extends Body> codec() {
        return CODEC;
    }
}
