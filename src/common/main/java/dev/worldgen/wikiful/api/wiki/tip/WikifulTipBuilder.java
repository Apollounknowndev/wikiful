package dev.worldgen.wikiful.api.wiki.tip;

import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class WikifulTipBuilder {
	private final EventTrigger trigger;
	private Identifier sprite = Tip.DEFAULT_SPRITE;
	private int width = 240;
	private int padding = 12;
	private float displayTimeMultiplier = 1f;
	private boolean cannotDismiss = false;
	private final List<Body> body = new ArrayList<>();
	
	private WikifulTipBuilder(EventTrigger trigger) {
		this.trigger = trigger;
	}
	
	public static WikifulTipBuilder builder(EventTrigger trigger) {
		return new WikifulTipBuilder(trigger);
	}
	
	public WikifulTipBuilder sprite(Identifier sprite) {
		this.sprite = sprite;
		return this;
	}
	
	public WikifulTipBuilder width(int width) {
		this.width = width;
		return this;
	}
	
	public WikifulTipBuilder padding(int padding) {
		this.padding = padding;
		return this;
	}
	
	public WikifulTipBuilder displayTimeMultiplier(float displayTimeMultiplier) {
		this.displayTimeMultiplier = displayTimeMultiplier;
		return this;
	}
	
	public WikifulTipBuilder cannotDismiss(boolean cannotDismiss) {
		this.cannotDismiss = cannotDismiss;
		return this;
	}
	
	public WikifulTipBuilder addBody(Body... body) {
		this.body.addAll(List.of(body));
		return this;
	}
	
	public Tip build() {
		return new Tip(this.trigger, this.sprite, this.width, this.padding, this.displayTimeMultiplier, this.cannotDismiss, this.body);
	}
}
