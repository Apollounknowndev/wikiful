package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.impl.wiki.body.*;
import net.msrandom.multiplatform.annotations.Expect;

public interface WikifulBodyTypes {
    @Expect
    static MapCodec<? extends Body> register(String name, MapCodec<? extends Body> codec);

    static void init() {
        register("text", TextBody.CODEC);
        register("item", ItemBody.CODEC);
        register("sprite", SpriteBody.CODEC);
        register("empty", EmptyBody.CODEC);
        register("horizontal_line", HorizontalLineBody.CODEC);
    }
}
