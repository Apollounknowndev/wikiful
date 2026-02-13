package dev.worldgen.wikiful.impl.client;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.api.client.BodyElementRegistry;
import dev.worldgen.wikiful.impl.WikifulClient;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;

import java.util.ArrayList;
import java.util.List;

public class TipToast implements Toast {
    private final Tip tip;
    private final int contentWidth;
    private float displayTime;

    private List<BodyElement> elements = null;
    private int height = 1;
	
	public TipToast(Tip tip) {
        this.tip = tip;
        this.contentWidth = tip.width() - 2 * tip.padding();
        this.displayTime = 10000 * tip.displayTimeMultiplier();
    }
    
    @Override
    public int width() {
        return tip.width();
    }

    @Override
    public int height() {
        return height;
    }
    
    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent component, long fullyVisibleFor) {
        if (WikifulClient.DISMISS_TIP.isDown() && !this.tip.cannotDismiss()) this.displayTime = 0;
        
	    Visibility wantedVisibility = fullyVisibleFor >= this.displayTime * component.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
        
        if (this.elements == null) {
            this.elements = new ArrayList<>();
            for (Body body : tip.body()) {
                this.elements.add(BodyElementRegistry.createElement(body));
            }
            if (!tip.cannotDismiss()) {
                this.elements.add(BodyElementRegistry.createElement(Tip.DISMISS_BODY));
            }
        }
        
        guiGraphics.blitSprite(tip.sprite(), 0, 0, this.width(), this.height());
        
        int y = tip.padding();
        for (BodyElement element : this.elements) {
            element.render(guiGraphics, Minecraft.getInstance(), tip.padding(), y, contentWidth);
            y += element.getHeight(Minecraft.getInstance(), contentWidth) + 2;
        }
        if (height == 1) {
            height = y + tip.padding() - 2;
        }
        
        return wantedVisibility;
    }
}
