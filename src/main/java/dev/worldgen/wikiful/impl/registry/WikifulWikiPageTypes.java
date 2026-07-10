package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.api.wiki.page.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.page.BaseWikiPage;

public interface WikifulWikiPageTypes {
    static void register(String name, MapCodec<? extends WikiPage> codec) {
        Wikiful.REGISTRAR.register(WikifulBuiltInRegistries.WIKI_PAGE_TYPE, name, codec);
    }

    static void init() {
        register("base", BaseWikiPage.CODEC);
    }
}