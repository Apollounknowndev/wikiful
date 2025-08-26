package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.registry.WikifulRegistryKeys;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;

public class WikifulEvents {
    public static void onRegistryLoad(RegistryAccess registries) {
        for (EventTriggerType<?> type : registries.lookupOrThrow(WikifulRegistryKeys.EVENT_TRIGGER_TYPE)) {
            type.clearListeners();
        }

        addListeners(registries.lookupOrThrow(WikifulRegistryKeys.TIP));
        addListeners(registries.lookupOrThrow(WikifulRegistryKeys.WIKI_SECTION));
        addListeners(registries.lookupOrThrow(WikifulRegistryKeys.WIKI_PAGE));
    }

    private static void addListeners(Registry<TriggerHolder> registry) {
        for (Holder.Reference<TriggerHolder> holder : registry.listElements().toList()) {
            holder.value().getTrigger().ifPresent(trigger -> trigger.type().addListener(holder, trigger));
        }
    }
}
