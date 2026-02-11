package dev.worldgen.wikiful.impl;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.msrandom.multiplatform.annotations.Expect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

public class Wikiful {
    public static final String MOD_ID = "wikiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Expect
    public static void init();
    
    @Expect
    public static <T> Registry<T> registry(RegistryAccess registries, ResourceKey<? extends Registry<T>> key);

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
    
    public static <T> Codec<List<T>> compactList(Codec<T> codec) {
        return Codec.withAlternative(codec.listOf(), codec, List::of);
    }

    public static <T> List<Holder.Reference<T>> getReferences(RegistryAccess registries, ResourceKey<Registry<T>> key) {
        return registries.lookupOrThrow(key).listElements().toList();
    }

    public static <T> List<Holder<T>> filterHolders(RegistryAccess registries, ResourceKey<Registry<T>> key, Predicate<Holder.Reference<T>> filter) {
        return registries.lookupOrThrow(key).listElements().filter(filter).map(holder -> (Holder<T>) holder).toList();
    }
}
