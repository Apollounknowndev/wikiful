package dev.worldgen.wikiful.impl.event.triggers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;

import java.util.List;

public record AnyOf(List<EventTrigger> triggers) implements EventTrigger {
    public static final MapCodec<AnyOf> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.CODEC.listOf().fieldOf("triggers").forGetter(AnyOf::triggers)
    ).apply(instance, AnyOf::new));

    @Override
    public EventTriggerType<? extends EventTrigger> type() {
        return WikifulEventTriggerTypes.ANY_OF;
    }
}
