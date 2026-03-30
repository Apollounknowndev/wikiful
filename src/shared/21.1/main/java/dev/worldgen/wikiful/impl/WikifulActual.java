package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.registry.WikifulBodyTypes;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import dev.worldgen.wikiful.impl.registry.WikifulWikiPageTypes;
import dev.worldgen.wikiful.impl.wiki.body.ItemBody;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.Optional;

public class WikifulActual {
    @Actual
    public static void init() {
        WikifulBuiltInRegistries.init();
        WikifulEventTriggerTypes.init();
        WikifulWikiPageTypes.init();
        WikifulBodyTypes.init();
        WikifulBodyTypes.register("item", ItemBody.CODEC);
        WikifulLootParamSets.init();
    }
    
    @Actual
    public static <T> Registry<T> registry(RegistryAccess registries, ResourceKey<? extends Registry<T>> key) {
        return registries.registryOrThrow(key);
    }
    
    @Actual
    public static boolean is(BlockState state, Optional<HolderSet<Block>> block) {
        return block.map(state::is).orElse(true);
    }
}
