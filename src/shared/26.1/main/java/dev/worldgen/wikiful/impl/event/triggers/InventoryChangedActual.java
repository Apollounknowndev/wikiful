package dev.worldgen.wikiful.impl.event.triggers;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.world.item.ItemStack;
import net.msrandom.multiplatform.annotations.Actual;

public class InventoryChangedActual {
	@Actual
	public static boolean test(ItemPredicate predicate, ItemStack stack) {
		return predicate.test(stack);
	}
}
