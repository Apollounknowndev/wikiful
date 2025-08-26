package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import dev.worldgen.wikiful.impl.registry.WikifulBodyTypes;
import dev.worldgen.wikiful.impl.registry.WikifulWikiPageTypes;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wikiful {
    public static final String MOD_ID = "wikiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        WikifulBuiltInRegistries.init();
        WikifulEventTriggerTypes.init();
        WikifulWikiPageTypes.init();
        WikifulBodyTypes.init();
        WikifulLootParamSets.init();
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
