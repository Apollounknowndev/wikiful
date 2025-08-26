package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import net.minecraft.core.Registry;
import net.msrandom.multiplatform.annotations.Actual;

public interface WikifulWikiPageTypesActual {
    @Actual
    static MapCodec<? extends WikiPage> register(String name, MapCodec<? extends WikiPage> codec) {
        return Registry.register(WikifulBuiltInRegistries.WIKI_PAGE_TYPE, Wikiful.id(name), codec);
    }
}
