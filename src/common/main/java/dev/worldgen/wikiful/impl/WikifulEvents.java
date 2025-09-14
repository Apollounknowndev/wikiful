package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;

public class WikifulEvents {
    public static void onRegistryLoad(RegistryAccess registries) {
        for (EventTriggerType<?> type : registries.lookupOrThrow(WikifulRegistries.EVENT_TRIGGER_TYPE)) {
            type.clearListeners();
        }

        addListeners(registries.lookupOrThrow(WikifulRegistries.TIP));
        addListeners(registries.lookupOrThrow(WikifulRegistries.SECTION));
        addListeners(registries.lookupOrThrow(WikifulRegistries.PAGE));
    }

    private static void addListeners(Registry<TriggerHolder> registry) {
        for (Holder.Reference<TriggerHolder> holder : registry.listElements().toList()) {
            holder.value().getTrigger().ifPresent(trigger -> trigger.type().addListener(holder, trigger));
        }
    }
}
