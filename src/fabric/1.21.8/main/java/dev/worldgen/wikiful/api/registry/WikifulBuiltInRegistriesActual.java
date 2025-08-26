package dev.worldgen.wikiful.api.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.msrandom.multiplatform.annotations.Actual;
import net.msrandom.multiplatform.annotations.Expect;
import org.checkerframework.checker.units.qual.A;

public class WikifulBuiltInRegistriesActual {
    @Actual
    public static final Registry<EventTriggerType<?>> EVENT_TRIGGER_TYPE = FabricRegistryBuilder.createSimple(WikifulRegistryKeys.EVENT_TRIGGER_TYPE).buildAndRegister();
    @Actual
    public static final Registry<MapCodec<? extends Body>> BODY_TYPE = FabricRegistryBuilder.createSimple(WikifulRegistryKeys.BODY_TYPE).buildAndRegister();
    @Actual
    public static final Registry<MapCodec<? extends WikiPage>> WIKI_PAGE_TYPE = FabricRegistryBuilder.createSimple(WikifulRegistryKeys.WIKI_PAGE_TYPE).buildAndRegister();
}
