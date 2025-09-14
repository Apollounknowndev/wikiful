package dev.worldgen.wikiful.impl.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.event.triggers.*;
import net.msrandom.multiplatform.annotations.Expect;

public interface WikifulEventTriggerTypes {
    EventTriggerType<AnyOf> ANY_OF = register("any_of", AnyOf.CODEC);
    EventTriggerType<DestroyBlock> DESTROY_BLOCK = register("destroy_block", DestroyBlock.CODEC);
    EventTriggerType<HitBlock> HIT_BLOCK = register("hit_block", HitBlock.CODEC);
    EventTriggerType<LocationChanged> LOCATION_CHANGED = register("location_changed", LocationChanged.CODEC);
    EventTriggerType<InventoryChanged> INVENTORY_CHANGED = register("inventory_changed", InventoryChanged.CODEC);

    @Expect
    static <T extends EventTrigger> EventTriggerType<T> register(String name, MapCodec<T> codec);

    static void init() {

    }
}
