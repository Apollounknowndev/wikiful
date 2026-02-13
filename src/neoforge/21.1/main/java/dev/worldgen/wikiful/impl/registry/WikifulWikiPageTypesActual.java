package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.WikifulEntrypoint;
import net.msrandom.multiplatform.annotations.Actual;

public interface WikifulWikiPageTypesActual {
    @Actual
    static MapCodec<? extends WikiPage> register(String name, MapCodec<? extends WikiPage> codec) {
        WikifulEntrypoint.WIKI_PAGE_TYPES.put(name, codec);
        return codec;
    }
}
