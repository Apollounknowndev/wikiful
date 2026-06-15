package dev.worldgen.wikiful.api.wiki.section;

import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.wiki.page.WikiPage;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection.Visibility;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.worldgen.wikiful.impl.wiki.page.section.WikiSection.Visibility.*;

public class WikifulSectionBuilder {
	private Optional<EventTrigger> trigger = Optional.empty();
	private Visibility visibility = VISIBLE;
	private final Component title;
	private final List<Body> body = new ArrayList<>();
	
	private WikifulSectionBuilder(Component title) {
		this.title = title;
	}
	
	public static WikifulSectionBuilder builder(Component title) {
		return new WikifulSectionBuilder(title);
	}
	
	public WikifulSectionBuilder trigger(EventTrigger trigger, Visibility visibility) {
		if (visibility == VISIBLE) throw new IllegalArgumentException("Wiki section with trigger should be locked or invisible");
		this.trigger = Optional.of(trigger);
		this.visibility = visibility;
		return this;
	}
	
	public WikifulSectionBuilder addBody(Body... body) {
		this.body.addAll(List.of(body));
		return this;
	}
	
	public WikiSection buildPage() {
		if (this.visibility != VISIBLE) throw new IllegalStateException("Wiki section inside of page should always be visible");
		return new WikiSection(Optional.empty(), this.trigger, this.visibility, this.title, this.body);
	}
	
	public WikiSection buildStandalone(Holder<WikiPage> parent) {
		return new WikiSection(Optional.of(parent), this.trigger, this.visibility, this.title, this.body);
	}
}
