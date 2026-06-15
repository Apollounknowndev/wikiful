package dev.worldgen.wikiful.api.wiki.body;

import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.body.ItemBody;
import net.minecraft.world.item.ItemStack;
import net.msrandom.classextensions.ClassExtension;
import net.msrandom.classextensions.ExtensionInject;

import java.util.List;

@ClassExtension(WikifulBodyElements.class)
public class WikifulBodyElementsExtension {
	@ExtensionInject
	public static Body item(ItemStack item) {
		return new ItemBody(List.of(item), true, true, true);
	}
	
	@ExtensionInject
	public static Body item(ItemStack item, boolean showDecorations, boolean showSlot) {
		return new ItemBody(List.of(item), showDecorations, showSlot, true);
	}
	
	@ExtensionInject
	public static Body item(ItemStack item, boolean showDecorations, boolean showSlot, boolean centered) {
		return new ItemBody(List.of(item), showDecorations, showSlot, centered);
	}
	
	@ExtensionInject
	public static Body item(List<ItemStack> items) {
		return new ItemBody(items, true, true, true);
	}
	
	@ExtensionInject
	public static Body item(List<ItemStack> items, boolean showDecorations, boolean showSlot) {
		return new ItemBody(items, showDecorations, showSlot, true);
	}
	
	@ExtensionInject
	public static Body item(List<ItemStack> items, boolean showDecorations, boolean showSlot, boolean centered) {
		return new ItemBody(items, showDecorations, showSlot, centered);
	}
}
