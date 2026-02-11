package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.minecraft.core.Registry;
import net.msrandom.multiplatform.annotations.Actual;

public interface WikifulBodyTypesActual {
    @Actual
    static MapCodec<? extends Body> register(String name, MapCodec<? extends Body> codec) {
        return Registry.register(WikifulBuiltInRegistries.BODY_TYPE, Wikiful.id(name), codec);
    }
}
