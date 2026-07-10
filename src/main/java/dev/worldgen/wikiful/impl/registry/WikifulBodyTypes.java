package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.body.*;

public interface WikifulBodyTypes {
    static void register(String name, MapCodec<? extends Body> codec) {
        Wikiful.REGISTRAR.register(WikifulBuiltInRegistries.BODY_TYPE, name, codec);
    }

    static void init() {
        register("text", TextBody.CODEC);
        register("sprite", SpriteBody.CODEC);
        register("empty", EmptyBody.CODEC);
        register("horizontal_line", HorizontalLineBody.CODEC);
        register("item", ItemBody.CODEC);
    }
}
