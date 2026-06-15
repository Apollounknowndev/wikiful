package dev.worldgen.wikiful.api.event;

import dev.worldgen.wikiful.impl.event.triggers.*;
import dev.worldgen.wikiful.impl.event.triggers.InventoryChanged.Slots;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Optional;

public class WikifulEventTriggers {
	public static EventTrigger anyOf(EventTrigger... triggers) {
		return new AnyOf(List.of(triggers));
	}
	
	public static EventTrigger blockBroken(Block... blocks) {
		return new BlockBroken(Optional.empty(), Optional.of(HolderSet.direct(Block::builtInRegistryHolder, blocks)), Optional.empty());
	}
	
	public static EventTrigger blockBroken(HolderSet<Block> blocks) {
		return new BlockBroken(Optional.empty(), Optional.of(blocks), Optional.empty());
	}
	
	public static EventTrigger blockBroken(LootItemCondition condition, HolderSet<Block> blocks) {
		return new BlockBroken(Optional.of(condition), Optional.of(blocks), Optional.empty());
	}
	
	public static EventTrigger blockBroken(StatePropertiesPredicate properties, HolderSet<Block> blocks) {
		return new BlockBroken(Optional.empty(), Optional.of(blocks), Optional.of(properties));
	}
	
	public static EventTrigger blockBroken(LootItemCondition condition, StatePropertiesPredicate properties, HolderSet<Block> blocks) {
		return new BlockBroken(Optional.of(condition), Optional.of(blocks), Optional.of(properties));
	}
	
	public static EventTrigger blockBroken(Optional<LootItemCondition> condition, Optional<StatePropertiesPredicate> properties) {
		return new BlockBroken(condition, Optional.empty(), properties);
	}
	
	public static EventTrigger blockHit(Block... blocks) {
		return new BlockHit(Optional.empty(), Optional.of(HolderSet.direct(Block::builtInRegistryHolder, blocks)), Optional.empty());
	}
	
	public static EventTrigger blockHit(HolderSet<Block> blocks) {
		return new BlockHit(Optional.empty(), Optional.of(blocks), Optional.empty());
	}
	
	public static EventTrigger blockHit(LootItemCondition condition, HolderSet<Block> blocks) {
		return new BlockHit(Optional.of(condition), Optional.of(blocks), Optional.empty());
	}
	
	public static EventTrigger blockHit(StatePropertiesPredicate properties, HolderSet<Block> blocks) {
		return new BlockHit(Optional.empty(), Optional.of(blocks), Optional.of(properties));
	}
	
	public static EventTrigger blockHit(LootItemCondition condition, StatePropertiesPredicate properties, HolderSet<Block> blocks) {
		return new BlockHit(Optional.of(condition), Optional.of(blocks), Optional.of(properties));
	}
	
	public static EventTrigger blockHit(Optional<LootItemCondition> condition, Optional<StatePropertiesPredicate> properties) {
		return new BlockHit(condition, Optional.empty(), properties);
	}
	
	public static EventTrigger inventoryChanged(ItemPredicate... items) {
		return new InventoryChanged(Optional.empty(), Slots.ANY, List.of(items));
	}
	
	public static EventTrigger inventoryChanged(LootItemCondition condition, ItemPredicate... items) {
		return new InventoryChanged(Optional.of(condition), Slots.ANY, List.of(items));
	}
	
	public static EventTrigger inventoryChanged(Optional<LootItemCondition> condition, Slots slots, ItemPredicate... items) {
		return new InventoryChanged(condition, slots, List.of(items));
	}
	
	public static EventTrigger locationChanged(LocationPredicate location) {
		return new LocationChanged(Optional.empty(), location);
	}
	
	public static EventTrigger locationChanged(LootItemCondition condition, LocationPredicate location) {
		return new LocationChanged(Optional.of(condition), location);
	}
	
	public static EventTrigger menuOpened(ResourceKey<MenuType<?>> menu) {
		return new MenuOpened(Optional.empty(), menu);
	}
	
	public static EventTrigger menuOpened(LootItemCondition condition, ResourceKey<MenuType<?>> menu) {
		return new MenuOpened(Optional.of(condition), menu);
	}
}
