package dev.worldgen.wikiful.impl.event.triggers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record BlockBroken(Optional<LootItemCondition> condition, Optional<HolderSet<Block>> blocks, Optional<StatePropertiesPredicate> properties) implements EventTrigger {
    public static final MapCodec<BlockBroken> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.conditionCodec(WikifulLootParamSets.BLOCK_INTERACTION).forGetter(BlockBroken::condition),
        RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("blocks").forGetter(BlockBroken::blocks),
        StatePropertiesPredicate.CODEC.optionalFieldOf("properties").forGetter(BlockBroken::properties)
    ).apply(instance, BlockBroken::new));

    public static void trigger(ServerLevel level, ServerPlayer player, ItemStack stack, Vec3 vec3, BlockState state) {
        LootContext context = context(level, player, stack, vec3, state);
        WikifulEventTriggerTypes.BLOCK_BROKEN.onListeners(player, trigger -> trigger instanceof BlockBroken hitBlock && hitBlock.matches(context, state));
    }

    private boolean matches(LootContext context, BlockState state) {
        if (!this.blocks.map(state::is).orElse(true)) return false;
        if (!this.properties.map(predicate -> predicate.matches(state)).orElse(true)) return false;
        return this.condition.map(predicate -> predicate.test(context)).orElse(true);
    }

    private static LootContext context(ServerLevel serverLevel, Entity entity, ItemStack stack, Vec3 vec3, BlockState state) {
        LootParams params = new LootParams.Builder(serverLevel)
            .withParameter(LootContextParams.THIS_ENTITY, entity)
            .withParameter(LootContextParams.ORIGIN, vec3)
            .withParameter(LootContextParams.TOOL, stack)
            .withParameter(LootContextParams.BLOCK_STATE, state)
            .create(WikifulLootParamSets.BLOCK_INTERACTION);
        return new LootContext.Builder(params).create(Optional.empty());
    }

    @Override
    public EventTriggerType<? extends EventTrigger> type() {
        return WikifulEventTriggerTypes.BLOCK_BROKEN;
    }
}
