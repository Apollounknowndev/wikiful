package dev.worldgen.wikiful.api.wiki.body;

import dev.worldgen.wikiful.impl.wiki.body.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.List;

public class WikifulBodyElements {
	public static Body empty(int spacing) {
		return new EmptyBody(spacing);
	}
	
	public static Body line() {
		return new HorizontalLineBody(1, 0, 0xFFFFFFFF);
	}
	
	public static Body line(int height) {
		return new HorizontalLineBody(1, 0, 0xFFFFFFFF);
	}
	
	public static Body line(int height, int spacing) {
		return new HorizontalLineBody(1, 0, 0xFFFFFFFF);
	}
	
	public static Body line(int height, int spacing, int color) {
		return new HorizontalLineBody(1, 0, color);
	}
	
	public static Body sprite(Identifier sprite, int textureWidth, int textureHeight) {
		return new SpriteBody(sprite, textureWidth, textureHeight, 0.5f, true);
	}
	
	public static Body sprite(Identifier sprite, int textureWidth, int textureHeight, float scale) {
		return new SpriteBody(sprite, textureWidth, textureHeight, scale, true);
	}
	
	public static Body sprite(Identifier sprite, int textureWidth, int textureHeight, float scale, boolean centered) {
		return new SpriteBody(sprite, textureWidth, textureHeight, scale, centered);
	}
	
	public static Body text(String contents) {
		return new TextBody(Component.literal(contents));
	}
	
	public static Body text(Component contents) {
		return new TextBody(contents);
	}
	
	public static Body item(ItemStackTemplate item) {
		return new ItemBody(List.of(item), true, true, true);
	}
	
	public static Body item(ItemStackTemplate item, boolean showDecorations, boolean showSlot) {
		return new ItemBody(List.of(item), showDecorations, showSlot, true);
	}
	
	public static Body item(ItemStackTemplate item, boolean showDecorations, boolean showSlot, boolean centered) {
		return new ItemBody(List.of(item), showDecorations, showSlot, centered);
	}
	
	public static Body item(List<ItemStackTemplate> items) {
		return new ItemBody(items, true, true, true);
	}
	
	public static Body item(List<ItemStackTemplate> items, boolean showDecorations, boolean showSlot) {
		return new ItemBody(items, showDecorations, showSlot, true);
	}
	
	public static Body item(List<ItemStackTemplate> items, boolean showDecorations, boolean showSlot, boolean centered) {
		return new ItemBody(items, showDecorations, showSlot, centered);
	}
}
