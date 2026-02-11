package dev.worldgen.wikiful.impl.event.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Optional;

public record InventoryChanged(Optional<LootItemCondition> condition, Slots slots, List<ItemPredicate> items) implements EventTrigger {
    public static final MapCodec<InventoryChanged> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.CONDITION_CODEC.forGetter(InventoryChanged::condition),
        Slots.CODEC.optionalFieldOf("slots", Slots.ANY).forGetter(InventoryChanged::slots),
        ItemPredicate.CODEC.listOf().optionalFieldOf("items", List.of()).forGetter(InventoryChanged::items)
    ).apply(instance, InventoryChanged::new));

    public static void trigger(ServerPlayer player, Inventory inventory, ItemStack changedStack) {
        LootContext context = EntityPredicate.createContext(player, player);
        WikifulEventTriggerTypes.INVENTORY_CHANGED.onListeners(player,
            trigger -> trigger instanceof InventoryChanged inventoryChanged && inventoryChanged.matches(context, player, inventory, changedStack)
        );
    }

    public boolean matches(Inventory inventory, ItemStack changedStack) {
        int full = 0;
        int empty = 0;
        int occupied = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack2 = inventory.getItem(i);
            if (itemStack2.isEmpty()) {
                empty++;
            } else {
                occupied++;
                if (itemStack2.getCount() >= itemStack2.getMaxStackSize()) {
                    full++;
                }
            }
        }

        if (!this.slots.matches(full, empty, occupied)) {
            return false;
        } else if (this.items.isEmpty()) {
            return true;
        } else if (this.items.size() != 1) {
            List<ItemPredicate> predicates = new ObjectArrayList<>(this.items);
            for(int i = 0; i < inventory.getContainerSize(); ++i) {
                if (predicates.isEmpty()) {
                    return true;
                }

                ItemStack stack = inventory.getItem(i);
                if (!stack.isEmpty()) {
                    predicates.removeIf((predicate) -> predicate.test(stack));
                }
            }

            return predicates.isEmpty();
        } else {
            return !changedStack.isEmpty() && this.items.getFirst().test(changedStack);
        }
    }

    private boolean matches(LootContext context, ServerPlayer player, Inventory inventory, ItemStack changedStack) {
        if (!this.matches(inventory, changedStack)) return false;
        return this.condition.map(predicate -> predicate.test(context)).orElse(true);
    }

    @Override
    public EventTriggerType<? extends EventTrigger> type() {
        return WikifulEventTriggerTypes.INVENTORY_CHANGED;
    }

    public record Slots(MinMaxBounds.Ints occupied, MinMaxBounds.Ints full, MinMaxBounds.Ints empty) {
        public static final Codec<Slots> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MinMaxBounds.Ints.CODEC.optionalFieldOf("occupied", MinMaxBounds.Ints.ANY).forGetter(Slots::occupied),
            MinMaxBounds.Ints.CODEC.optionalFieldOf("full", MinMaxBounds.Ints.ANY).forGetter(Slots::full),
            MinMaxBounds.Ints.CODEC.optionalFieldOf("empty", MinMaxBounds.Ints.ANY).forGetter(Slots::empty)
        ).apply(instance, Slots::new));
        public static final Slots ANY = new Slots(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY);

        public boolean matches(int full, int empty, int occupied) {
            if (!this.full.matches(full)) {
                return false;
            } else if (!this.empty.matches(empty)) {
                return false;
            } else {
                return this.occupied.matches(occupied);
            }
        }
    }
}
