package dev.worldgen.wikiful.api.wiki.page;

import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.wiki.page.CommonWikiData.Sprites;
import dev.worldgen.wikiful.api.wiki.section.WikifulSectionBuilder;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.page.BaseWikiPage;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WikifulPageBuilder {
	private Optional<EventTrigger> trigger = Optional.empty();
	private final Component title;
	private boolean showOnMainPage = true;
	private Sprites sprites = Sprites.DEFAULT;
	private final List<Body> opener = new ArrayList<>();
	private final List<WikiSection> sections = new ArrayList<>();
	
	private WikifulPageBuilder(Component title) {
		this.title = title;
	}
	
	public static WikifulPageBuilder builder(Component title) {
		return new WikifulPageBuilder(title);
	}
	
	public WikifulPageBuilder trigger(EventTrigger trigger) {
		this.trigger = Optional.of(trigger);
		return this;
	}
	
	public WikifulPageBuilder hideFromMainPage() {
		this.showOnMainPage = false;
		return this;
	}
	
	public WikifulPageBuilder background(Identifier background) {
		this.sprites = new Sprites(background);
		return this;
	}
	
	public WikifulPageBuilder addBody(Body... body) {
		this.opener.addAll(List.of(body));
		return this;
	}
	
	public WikifulPageBuilder addSection(WikifulSectionBuilder... section) {
		this.sections.addAll(Arrays.stream(section).map(WikifulSectionBuilder::buildPage).toList());
		return this;
	}
	
	public WikifulPageBuilder addSection(WikiSection... section) {
		this.sections.addAll(List.of(section));
		return this;
	}
	
	public CommonWikiData buildCommonData() {
		return new CommonWikiData(this.trigger, this.title, this.showOnMainPage, this.sprites, this.opener, this.sections);
	}
	
	public WikiPage build() {
		return new BaseWikiPage(this.buildCommonData());
	}
}
