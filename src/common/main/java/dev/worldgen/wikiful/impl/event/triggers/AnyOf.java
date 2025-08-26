package dev.worldgen.wikiful.impl.event.triggers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Optional;

public record AnyOf(List<EventTrigger> triggers) implements EventTrigger {
    public static final MapCodec<AnyOf> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.CODEC.listOf().fieldOf("triggers").forGetter(AnyOf::triggers)
    ).apply(instance, AnyOf::new));

    @Override
    public EventTriggerType<? extends EventTrigger> type() {
        return WikifulEventTriggerTypes.ANY_OF;
    }
}
