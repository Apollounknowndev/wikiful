package dev.worldgen.wikiful.api.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import net.minecraft.core.Registry;

import net.msrandom.multiplatform.annotations.Expect;

public class WikifulBuiltInRegistries {
    @Expect
    public static final Registry<EventTriggerType<?>> EVENT_TRIGGER_TYPE;
    @Expect
    public static final Registry<MapCodec<? extends Body>> BODY_TYPE;
    @Expect
    public static final Registry<MapCodec<? extends WikiPage>> WIKI_PAGE_TYPE;

    public static void init() {
    }
}
