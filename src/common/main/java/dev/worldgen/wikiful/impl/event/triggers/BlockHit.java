package dev.worldgen.wikiful.impl.event.triggers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.event.LootContextHelper;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record BlockHit(Optional<LootItemCondition> condition, Optional<HolderSet<Block>> blocks, Optional<StatePropertiesPredicate> properties) implements EventTrigger {
    public static final MapCodec<BlockHit> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.CONDITION_CODEC.forGetter(BlockHit::condition),
        RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("blocks").forGetter(BlockHit::blocks),
        StatePropertiesPredicate.CODEC.optionalFieldOf("properties").forGetter(BlockHit::properties)
    ).apply(instance, BlockHit::new));

    public static void trigger(ServerLevel level, ServerPlayer player, ItemStack stack, Vec3 vec3, BlockState state) {
        LootContext context = LootContextHelper.buildBlockInteractionContext(level, player, stack, vec3, state);
        WikifulEventTriggerTypes.BLOCK_HIT.onListeners(player, trigger -> trigger instanceof BlockHit blockHit && blockHit.matches(context, state));
    }

    private boolean matches(LootContext context, BlockState state) {
        if (!this.blocks.map(state::is).orElse(true)) return false;
        if (!this.properties.map(predicate -> predicate.matches(state)).orElse(true)) return false;
        return this.condition.map(predicate -> predicate.test(context)).orElse(true);
    }

    @Override
    public EventTriggerType<? extends EventTrigger> type() {
        return WikifulEventTriggerTypes.BLOCK_HIT;
    }
}
