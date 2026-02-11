package dev.worldgen.wikiful.impl.client.screen.element.backport;

import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class WikifulContainerWidget extends AbstractScrollArea implements ContainerEventHandler {
	private @Nullable GuiEventListener focused;
	private boolean isDragging;
	
	public WikifulContainerWidget(int $$0, int $$1, int $$2, int $$3, Component $$4) {
		super($$0, $$1, $$2, $$3, $$4);
	}
	
	public final boolean isDragging() {
		return this.isDragging;
	}
	
	public final void setDragging(boolean $$0) {
		this.isDragging = $$0;
	}
	
	public @Nullable GuiEventListener getFocused() {
		return this.focused;
	}
	
	public void setFocused(@Nullable GuiEventListener $$0) {
		if (this.focused != null) {
			this.focused.setFocused(false);
		}
		
		if ($$0 != null) {
			$$0.setFocused(true);
		}
		
		this.focused = $$0;
	}
	
	public @Nullable ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
		return super.nextFocusPath($$0);
	}
	
	public boolean mouseClicked(double x, double y, int buttonInfo) {
		boolean $$2 = this.updateScrolling(x, y, buttonInfo);
		return super.mouseClicked(x, y, buttonInfo) || $$2;
	}
	
	public boolean mouseReleased(double x, double y, int buttonInfo) {
		super.mouseReleased(x, y, buttonInfo);
		return super.mouseReleased(x, y, buttonInfo);
	}
	
	public boolean mouseDragged(double x, double y, int buttonInfo, double $$1, double $$2) {
		super.mouseDragged(x, y, buttonInfo, $$1, $$2);
		return super.mouseDragged(x, y, buttonInfo, $$1, $$2);
	}
	
	public boolean isFocused() {
		return super.isFocused();
	}
	
	public void setFocused(boolean $$0) {
		super.setFocused($$0);
	}
}