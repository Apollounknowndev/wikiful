package dev.worldgen.wikiful.impl.wiki.body;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.function.Function;

public interface Body {
    Codec<Body> BASE_CODEC = WikifulBuiltInRegistries.BODY_TYPE.byNameCodec().dispatch(Body::codec, Function.identity());
    Codec<Body> FULL_CODEC = Codec.withAlternative(
        Codec.withAlternative(
            BASE_CODEC,
            ComponentSerialization.CODEC,
            TextBody::new
        ),
        ExtraCodecs.NON_NEGATIVE_INT,
        EmptyBody::new
    );
    Codec<List<Body>> LIST_CODEC = Wikiful.compactList(FULL_CODEC);

    MapCodec<? extends Body> codec();
}
