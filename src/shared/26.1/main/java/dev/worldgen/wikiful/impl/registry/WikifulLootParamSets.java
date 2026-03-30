package dev.worldgen.wikiful.impl.registry;

import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.mixin.LootContextParamSetsAccessor;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.function.UnaryOperator;

public interface WikifulLootParamSets {
    ContextKeySet BLOCK_INTERACTION = register("block_interaction", builder -> builder
        .required(LootContextParams.THIS_ENTITY)
        .required(LootContextParams.ORIGIN)
        .optional(LootContextParams.TOOL)
        .required(LootContextParams.BLOCK_STATE)
    );
    ContextKeySet PLAYER_ONLY = register("player_only", builder -> builder
        .required(LootContextParams.THIS_ENTITY)
        .required(LootContextParams.ORIGIN)
    );

    private static ContextKeySet register(String name, UnaryOperator<ContextKeySet.Builder> builder) {
        ContextKeySet set = builder.apply(new ContextKeySet.Builder()).build();
        LootContextParamSetsAccessor.getRegistry().put(Wikiful.id(name), set);
        return set;
    }

    static void init() {

    }
}
