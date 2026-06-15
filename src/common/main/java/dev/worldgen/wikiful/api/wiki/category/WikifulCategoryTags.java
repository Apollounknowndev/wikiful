package dev.worldgen.wikiful.api.wiki.category;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.category.WikiCategory;
import net.minecraft.tags.TagKey;

public interface WikifulCategoryTags {
	TagKey<WikiCategory> BIOMES = create("biomes");
	TagKey<WikiCategory> BLOCKS = create("blocks");
	TagKey<WikiCategory> ENCHANTMENTS = create("enchantments");
	TagKey<WikiCategory> ITEMS = create("items");
	TagKey<WikiCategory> MOBS = create("mobs");
	TagKey<WikiCategory> STATUS_EFFECTS = create("status_effects");
	TagKey<WikiCategory> STRUCTURES = create("structures");
	
	private static TagKey<WikiCategory> create(String name) {
		return TagKey.create(WikifulRegistries.CATEGORY, Wikiful.id(name));
	}
}
