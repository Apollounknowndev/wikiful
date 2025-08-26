package dev.worldgen.wikiful.api.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface WikifulRegistryKeys {
    // Static
    ResourceKey<Registry<EventTriggerType<?>>> EVENT_TRIGGER_TYPE = create("event_trigger_type");
    ResourceKey<Registry<MapCodec<? extends Body>>> BODY_TYPE = create("body_type");
    ResourceKey<Registry<MapCodec<? extends WikiPage>>> WIKI_PAGE_TYPE = create("wiki_page_type");
    // Dynamic
    ResourceKey<Registry<Tip>> TIP = create("tip");
    ResourceKey<Registry<WikiPage>> WIKI_PAGE = create("wiki_page");
    ResourceKey<Registry<WikiSection>> WIKI_SECTION = create("wiki_section");

    private static <T> ResourceKey<Registry<T>> create(String name) {
        return ResourceKey.createRegistryKey(Wikiful.id(name));
    }
}
