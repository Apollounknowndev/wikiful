package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.RegistryAccess;

public class WikifulEvents {
    public static void onRegistryLoad(RegistryAccess registries) {
        for (EventTriggerType<?> type : Wikiful.registry(registries, WikifulRegistries.EVENT_TRIGGER_TYPE).stream().toList()) {
            type.clearListeners();
        }

        addListeners(registries.lookupOrThrow(WikifulRegistries.TIP));
        addListeners(registries.lookupOrThrow(WikifulRegistries.SECTION));
        addListeners(registries.lookupOrThrow(WikifulRegistries.PAGE));
    }

    private static void addListeners(RegistryLookup<TriggerHolder> registry) {
        for (Holder.Reference<TriggerHolder> holder : registry.listElements().toList()) {
            holder.value().getTrigger().ifPresent(trigger -> trigger.type().addListener(holder, trigger));
        }
    }
}
