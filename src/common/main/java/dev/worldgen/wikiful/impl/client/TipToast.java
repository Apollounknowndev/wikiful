package dev.worldgen.wikiful.impl.client;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.api.client.BodyElementRegistry;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import dev.worldgen.wikiful.impl.wiki.body.EmptyBody;
import dev.worldgen.wikiful.impl.wiki.body.ItemBody;
import dev.worldgen.wikiful.impl.wiki.body.TextBody;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TipToast implements Toast {
    public static final ResourceLocation SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/slot");

    private final Tip tip;
    private final int contentWidth;
    private final float displayTime;

    private List<BodyElement> elements = null;
    private int height = 1;

    private Toast.Visibility wantedVisibility = Toast.Visibility.HIDE;

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
    public Visibility getWantedVisibility() {
        return this.wantedVisibility;
    }

    @Override
    public void update(ToastManager toastManager, long l) {
        this.wantedVisibility = l >= this.displayTime * toastManager.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, long fullyVisibleFor) {
        if (this.elements == null) {
            this.elements = new ArrayList<>();
            for (Body body : tip.body()) {
                this.elements.add(BodyElementRegistry.createElement(body));
            }
        }

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, tip.sprite(), 0, 0, this.width(), this.height());

        int y = tip.padding();
        for (BodyElement element : this.elements) {
            element.render(guiGraphics, Minecraft.getInstance(), tip.padding(), y, contentWidth);
            y += element.getHeight(Minecraft.getInstance(), contentWidth) + 2;
        }
        if (height == 1) {
            height = y + tip.padding() - 2;
        }
    }
}
