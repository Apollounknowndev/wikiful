package dev.worldgen.wikiful.impl.client.screen.element.backport;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public abstract class AbstractScrollArea extends AbstractWidget {
	private double scrollAmount;
	private static final Identifier SCROLLER_SPRITE = Identifier.withDefaultNamespace("widget/scroller");
	private static final Identifier SCROLLER_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("widget/scroller_background");
	private boolean scrolling;
	
	public AbstractScrollArea(int $$0, int $$1, int $$2, int $$3, Component $$4) {
		super($$0, $$1, $$2, $$3, $$4);
	}
	
	public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
		if (!this.visible) {
			return false;
		} else {
			this.setScrollAmount(this.scrollAmount() - $$3 * this.scrollRate());
			return true;
		}
	}
	
	public boolean mouseDragged(double x, double y, int buttonInfo, double $$1, double $$2) {
		if (this.scrolling) {
			if (y < (double)this.getY()) {
				this.setScrollAmount(0.0F);
			} else if (y > (double)this.getBottom()) {
				this.setScrollAmount(this.maxScrollAmount());
			} else {
				double $$3 = Math.max(1, this.maxScrollAmount());
				int $$4 = this.scrollerHeight();
				double $$5 = Math.max(1.0F, $$3 / (double)(this.height - $$4));
				this.setScrollAmount(this.scrollAmount() + $$2 * $$5);
			}
			
			return true;
		} else {
			return super.mouseDragged(x, y, buttonInfo, $$1, $$2);
		}
	}
	
	public void onRelease(double x, double y) {
		this.scrolling = false;
	}
	
	public double scrollAmount() {
		return this.scrollAmount;
	}
	
	public void setScrollAmount(double $$0) {
		this.scrollAmount = Mth.clamp($$0, (double)0.0F, (double)this.maxScrollAmount());
	}
	
	public boolean updateScrolling(double x, double y, int buttonInfo) {
		this.scrolling = this.scrollbarVisible() && this.isValidClickButton(buttonInfo) && this.isOverScrollbar(x, y);
		return this.scrolling;
	}
	
	protected boolean isOverScrollbar(double $$0, double $$1) {
		return $$0 >= (double)this.scrollBarX() && $$0 <= (double)(this.scrollBarX() + 6) && $$1 >= (double)this.getY() && $$1 < (double)this.getBottom();
	}
	
	public void refreshScrollAmount() {
		this.setScrollAmount(this.scrollAmount);
	}
	
	public int maxScrollAmount() {
		return Math.max(0, this.contentHeight() - this.height);
	}
	
	protected boolean scrollbarVisible() {
		return this.maxScrollAmount() > 0;
	}
	
	protected int scrollerHeight() {
		return Mth.clamp((int)((float)(this.height * this.height) / (float)this.contentHeight()), 32, this.height - 8);
	}
	
	protected int scrollBarX() {
		return this.getRight() - 6;
	}
	
	protected int scrollBarY() {
		return Math.max(this.getY(), (int)this.scrollAmount * (this.height - this.scrollerHeight()) / this.maxScrollAmount() + this.getY());
	}
	
	protected void renderScrollbar(GuiGraphics guiGraphics, int $$1, int $$2) {
		if (this.scrollbarVisible()) {
			int $$3 = this.scrollBarX();
			int $$4 = this.scrollerHeight();
			int $$5 = this.scrollBarY();
			guiGraphics.blitSprite(SCROLLER_BACKGROUND_SPRITE, $$3, this.getY(), 6, this.getHeight());
			guiGraphics.blitSprite(SCROLLER_SPRITE, $$3, $$5, 6, $$4);
		}
		
	}
	
	protected abstract int contentHeight();
	
	protected abstract double scrollRate();
}
