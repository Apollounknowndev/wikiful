package dev.worldgen.wikiful.api.wiki;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record CommonWikiData(Optional<EventTrigger> trigger, Component title, Sprites sprites, List<Body> opener, List<WikiSection> sections) {
    public static final MapCodec<CommonWikiData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.CODEC.optionalFieldOf("trigger").forGetter(CommonWikiData::trigger),
        ComponentSerialization.CODEC.fieldOf("title").forGetter(CommonWikiData::title),
        Sprites.CODEC.fieldOf("sprites").orElse(Sprites.DEFAULT).forGetter(CommonWikiData::sprites),
        Body.LIST_CODEC.fieldOf("opener").forGetter(CommonWikiData::opener),
        WikiSection.PAGE_CODEC.listOf().optionalFieldOf("sections", List.of()).forGetter(CommonWikiData::sections)
    ).apply(instance, CommonWikiData::new));

    public record Sprites(ResourceLocation background) {
        private static final ResourceLocation DEFAULT_BACKGROUND = Wikiful.id("page/box");
        public static final Sprites DEFAULT = new Sprites(DEFAULT_BACKGROUND);
        public static final Codec<Sprites> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("background").orElse(DEFAULT_BACKGROUND).forGetter(Sprites::background)
        ).apply(instance, Sprites::new));
    }
}