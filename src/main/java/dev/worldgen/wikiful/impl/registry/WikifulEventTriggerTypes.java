package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.triggers.*;

public interface WikifulEventTriggerTypes {
    EventTriggerType<AnyOf> ANY_OF = register("any_of", AnyOf.CODEC);
    EventTriggerType<BlockBroken> BLOCK_BROKEN = register("block_broken", BlockBroken.CODEC);
    EventTriggerType<BlockHit> BLOCK_HIT = register("block_hit", BlockHit.CODEC);
    EventTriggerType<LocationChanged> LOCATION_CHANGED = register("location_changed", LocationChanged.CODEC);
    EventTriggerType<InventoryChanged> INVENTORY_CHANGED = register("inventory_changed", InventoryChanged.CODEC);
    EventTriggerType<MenuOpened> MENU_OPENED = register("menu_opened", MenuOpened.CODEC);

    static <T extends EventTrigger> EventTriggerType<T> register(String name, MapCodec<T> codec) {
        return Wikiful.REGISTRAR.register(WikifulBuiltInRegistries.EVENT_TRIGGER_TYPE, name, new EventTriggerType<>(codec));
    }

    static void init() {

    }
}
