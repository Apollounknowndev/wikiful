package dev.worldgen.wikiful.impl.wiki.page;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.wiki.CommonWikiData;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;

import java.util.List;

public record BaseWikiPage(CommonWikiData commonData) implements WikiPage {
    public static final MapCodec<BaseWikiPage> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        CommonWikiData.CODEC.forGetter(BaseWikiPage::commonData)
    ).apply(instance, BaseWikiPage::new));

    @Override
    public List<WikiSection> builtInSections() {
        return List.of();
    }

    @Override
    public MapCodec<? extends WikiPage> codec() {
        return CODEC;
    }
}
