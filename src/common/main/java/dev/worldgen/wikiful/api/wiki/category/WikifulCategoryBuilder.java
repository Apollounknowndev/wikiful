package dev.worldgen.wikiful.api.wiki.category;

import dev.worldgen.wikiful.api.wiki.page.WikiPage;
import dev.worldgen.wikiful.impl.wiki.category.WikiCategory;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public class WikifulCategoryBuilder {
	private final Component title;
	private Optional<Component> externalTitle = Optional.empty();
	private HolderSet<WikiPage> pages = HolderSet.empty();
	private HolderSet<WikiCategory> categories = HolderSet.empty();
	private boolean showIfEmpty = true;
	
	private WikifulCategoryBuilder(Component title) {
		this.title = title;
	}
	
	public static WikifulCategoryBuilder builder(Component title) {
		return new WikifulCategoryBuilder(title);
	}
	
	public WikifulCategoryBuilder externalTitle(Component externalTitle) {
		this.externalTitle = Optional.of(externalTitle);
		return this;
	}
	
	public WikifulCategoryBuilder pages(HolderSet<WikiPage> pages) {
		this.pages = pages;
		return this;
	}
	
	public WikifulCategoryBuilder categories(HolderSet<WikiCategory> categories) {
		this.categories = categories;
		return this;
	}
	
	public WikifulCategoryBuilder hideIfEmpty() {
		this.showIfEmpty = false;
		return this;
	}
	
	public WikiCategory build() {
		return new WikiCategory(this.title, this.externalTitle, this.pages, this.categories, this.showIfEmpty);
	}
}
