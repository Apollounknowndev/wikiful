package dev.worldgen.wikiful.api.event;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public interface EventTrigger {
    Codec<EventTrigger> CODEC = WikifulBuiltInRegistries.EVENT_TRIGGER_TYPE.byNameCodec().dispatch(EventTrigger::type, EventTriggerType::codec);

    EventTriggerType<? extends EventTrigger> type();

    static MapCodec<Optional<LootItemCondition>> conditionCodec(ContextKeySet contextKeySet) {
        return LootItemCondition.DIRECT_CODEC.validate(predicate -> {
            ProblemReporter.Collector collector = new ProblemReporter.Collector();
            ValidationContext validationContext = new ValidationContext(collector, contextKeySet);
            predicate.validate(validationContext);
            if (!collector.isEmpty()) {
                return DataResult.error(() -> "Validation error in event trigger condition: " + collector.getReport());
            }
            return DataResult.success(predicate);
        }).optionalFieldOf("condition");
    }
}