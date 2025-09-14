package dev.worldgen.wikiful.api.event;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import dev.worldgen.wikiful.impl.event.UnlockedSections;
import dev.worldgen.wikiful.impl.event.UnlockedTips;
import dev.worldgen.wikiful.impl.event.triggers.AnyOf;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public class EventTriggerType<T extends EventTrigger> {
    private final Map<Holder.Reference<TriggerHolder>, EventTrigger> listeners = new HashMap<>();

    private final MapCodec<T> codec;

    public EventTriggerType(MapCodec<T> codec) {
        this.codec = codec;
    }

    public MapCodec<T> codec() {
        return codec;
    }

    public void clearListeners() {
        listeners.clear();
    }

    public void addListener(Holder.Reference<TriggerHolder> holder, EventTrigger trigger) {
        if (trigger instanceof AnyOf(List<EventTrigger> triggers)) {
            for (EventTrigger subtrigger : triggers) {
                subtrigger.type().addListener(holder, subtrigger);
            }
        } else {
            listeners.put(holder, trigger);
        }
    }

    public void onListeners(ServerPlayer player, Predicate<EventTrigger> matcher) {
        for (Map.Entry<Holder.Reference<TriggerHolder>, EventTrigger> listener : this.listeners.entrySet()) {
            EventTrigger trigger = listener.getValue();
            ResourceKey<TriggerHolder> key = listener.getKey().key();
            ResourceLocation name = key.location();
            if (key.isFor(WikifulRegistries.TIP) && UnlockedTips.INSTANCE.hasUnlocked(player, name)) continue;
            if (key.isFor(WikifulRegistries.PAGE) && UnlockedPages.INSTANCE.hasUnlocked(player, name)) continue;
            if (key.isFor(WikifulRegistries.SECTION) && UnlockedSections.INSTANCE.hasUnlocked(player, name)) continue;

            if (matcher.test(trigger)) {
                listener.getKey().value().onTriggered(player, name);
            }
        }
    }
}
