package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistriesActual;
import dev.worldgen.wikiful.impl.Wikiful;
import net.minecraft.core.Registry;
import net.msrandom.multiplatform.annotations.Actual;
import net.msrandom.multiplatform.annotations.Expect;

public interface WikifulEventTriggerTypesActual {
    @Actual
    static <T extends EventTrigger> EventTriggerType<T> register(String name, MapCodec<T> codec) {
        return Registry.register(WikifulBuiltInRegistries.EVENT_TRIGGER_TYPE, Wikiful.id(name), new EventTriggerType<>(codec));
    }
}
