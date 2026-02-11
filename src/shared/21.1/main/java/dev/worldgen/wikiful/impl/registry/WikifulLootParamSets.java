package dev.worldgen.wikiful.impl.registry;

import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.mixin.LootContextParamSetsAccessor;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.function.UnaryOperator;

public interface WikifulLootParamSets {
    LootContextParamSet BLOCK_INTERACTION = register("block_interaction", builder -> builder
        .required(LootContextParams.THIS_ENTITY)
        .required(LootContextParams.ORIGIN)
        .optional(LootContextParams.TOOL)
        .required(LootContextParams.BLOCK_STATE)
    );
    LootContextParamSet PLAYER_ONLY = register("player_only", builder -> builder
        .required(LootContextParams.THIS_ENTITY)
        .required(LootContextParams.ORIGIN)
    );

    private static LootContextParamSet register(String name, UnaryOperator<LootContextParamSet.Builder> builder) {
        LootContextParamSet set = builder.apply(new LootContextParamSet.Builder()).build();
        LootContextParamSetsAccessor.getRegistry().put(Wikiful.id(name), set);
        return set;
    }

    static void init() {

    }
}
