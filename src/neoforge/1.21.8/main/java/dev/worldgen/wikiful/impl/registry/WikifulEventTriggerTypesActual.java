package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.WikifulEntrypoint;
import net.minecraft.core.Registry;
import net.msrandom.multiplatform.annotations.Actual;

public interface WikifulEventTriggerTypesActual {
    @Actual
    static <T extends EventTrigger> EventTriggerType<T> register(String name, MapCodec<T> codec) {
        EventTriggerType<T> type = new EventTriggerType<>(codec);
        WikifulEntrypoint.TRIGGER_TYPES.put(name, type);
        return type;
    }
}
