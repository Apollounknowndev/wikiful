package dev.worldgen.wikiful.api.event;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public interface EventTrigger {
    Codec<EventTrigger> CODEC = WikifulBuiltInRegistries.EVENT_TRIGGER_TYPE.byNameCodec().dispatch(EventTrigger::type, EventTriggerType::codec);
    MapCodec<Optional<LootItemCondition>> CONDITION_CODEC = LootItemCondition.DIRECT_CODEC.optionalFieldOf("condition");
    
    EventTriggerType<? extends EventTrigger> type();
}