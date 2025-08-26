package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.wiki.page.BaseWikiPage;
import net.msrandom.multiplatform.annotations.Expect;

public interface WikifulWikiPageTypes {
    @Expect
    static MapCodec<? extends WikiPage> register(String name, MapCodec<? extends WikiPage> codec);

    static void init() {
        register("base", BaseWikiPage.CODEC);
    }
}
