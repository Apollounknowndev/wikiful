package dev.worldgen.wikiful.impl.event.triggers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public record LocationChanged(Optional<LootItemCondition> condition, LocationPredicate location) implements EventTrigger {
    public static final MapCodec<LocationChanged> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.conditionCodec(LootContextParamSets.ADVANCEMENT_ENTITY).forGetter(LocationChanged::condition),
        LocationPredicate.CODEC.fieldOf("location").forGetter(LocationChanged::location)
    ).apply(instance, LocationChanged::new));

    public static void trigger(ServerLevel level, ServerPlayer player) {
        LootContext context = EntityPredicate.createContext(player, player);
        WikifulEventTriggerTypes.LOCATION_CHANGED.onListeners(player,
            trigger -> trigger instanceof LocationChanged locationChanged && locationChanged.matches(context, level, player)
        );
    }

    private boolean matches(LootContext context, ServerLevel level, ServerPlayer player) {
        if (!this.location.matches(level, player.getX(), player.getY(), player.getZ())) return false;
        return this.condition.map(predicate -> predicate.test(context)).orElse(true);
    }

    @Override
    public EventTriggerType<? extends EventTrigger> type() {
        return WikifulEventTriggerTypes.LOCATION_CHANGED;
    }
}
