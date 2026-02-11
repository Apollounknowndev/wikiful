package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.registry.WikifulBodyTypes;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import dev.worldgen.wikiful.impl.registry.WikifulWikiPageTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.msrandom.multiplatform.annotations.Actual;

public class WikifulActual {
    @Actual
    public static void init() {
        WikifulBuiltInRegistries.init();
        WikifulEventTriggerTypes.init();
        WikifulWikiPageTypes.init();
        WikifulBodyTypes.init();
        WikifulLootParamSets.init();
    }
    
    @Actual
    public static <T> Registry<T> registry(RegistryAccess registries, ResourceKey<? extends Registry<T>> key) {
        return registries.registryOrThrow(key);
    }
}
