package dev.worldgen.wikiful.impl.event;

import dev.worldgen.wikiful.api.event.EventTrigger;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public interface TriggerHolder {
    Optional<EventTrigger> getTrigger();

    void onTriggered(ServerPlayer player, ResourceLocation id);
}
