package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import dev.worldgen.wikiful.impl.registry.WikifulBodyTypes;
import dev.worldgen.wikiful.impl.registry.WikifulWikiPageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

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

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

    public static <T> List<Holder.Reference<T>> getReferences(RegistryAccess registries, ResourceKey<Registry<T>> key) {
        return registries.lookupOrThrow(key).listElements().toList();
    }

    public static <T> List<Holder<T>> getHolders(RegistryAccess registries, ResourceKey<Registry<T>> key) {
        return registries.lookupOrThrow(key).listElements().map(reference -> (Holder<T>) reference).toList();
    }

    public static <T> List<T> getValues(RegistryAccess registries, ResourceKey<Registry<T>> key) {
        return registries.lookupOrThrow(key).stream().toList();
    }

    public static <T> List<Holder<T>> filterHolders(RegistryAccess registries, ResourceKey<Registry<T>> key, Predicate<Holder.Reference<T>> filter) {
        return registries.lookupOrThrow(key).listElements().filter(filter::test).map(holder -> (Holder<T>) holder).toList();
    }
}
