package dev.worldgen.wikiful.impl.wiki.body;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.ExtraCodecs;

public record EmptyBody(int spacing) implements Body {
    public static final MapCodec<EmptyBody> CODEC = ExtraCodecs.NON_NEGATIVE_INT.fieldOf("spacing").xmap(EmptyBody::new, EmptyBody::spacing);

    @Override
    public MapCodec<? extends Body> codec() {
        return CODEC;
    }
}
