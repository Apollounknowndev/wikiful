package dev.worldgen.wikiful.api.wiki.page;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import net.minecraft.tags.TagKey;

public interface WikifulPageTags {
	TagKey<WikiPage> BIOMES = create("biomes");
	TagKey<WikiPage> BLOCKS = create("blocks");
	TagKey<WikiPage> ENCHANTMENTS = create("enchantments");
	TagKey<WikiPage> ITEMS = create("items");
	TagKey<WikiPage> MOBS = create("mobs");
	TagKey<WikiPage> STATUS_EFFECTS = create("status_effects");
	TagKey<WikiPage> STRUCTURES = create("structures");
	
	private static TagKey<WikiPage> create(String name) {
		return TagKey.create(WikifulRegistries.PAGE, Wikiful.id(name));
	}
}
