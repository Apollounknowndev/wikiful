package dev.worldgen.wikiful.api.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.WikifulEntrypoint;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.minecraft.core.Registry;
import net.msrandom.multiplatform.annotations.Actual;

public class WikifulBuiltInRegistriesActual {
    @Actual
    public static final Registry<EventTriggerType<?>> EVENT_TRIGGER_TYPE = WikifulEntrypoint.DEFERRED_TRIGGER_TYPES.makeRegistry(b -> b.sync(true));
    @Actual
    public static final Registry<MapCodec<? extends Body>> BODY_TYPE = WikifulEntrypoint.DEFERRED_BODY_TYPES.makeRegistry(b -> b.sync(true));
    @Actual
    public static final Registry<MapCodec<? extends WikiPage>> WIKI_PAGE_TYPE = WikifulEntrypoint.DEFERRED_WIKI_PAGE_TYPES.makeRegistry(b -> b.sync(true));
}
