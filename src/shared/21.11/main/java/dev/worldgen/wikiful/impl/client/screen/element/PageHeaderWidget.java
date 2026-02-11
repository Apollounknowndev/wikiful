package dev.worldgen.wikiful.impl.client.screen.element;

import dev.worldgen.wikiful.api.wiki.WikiPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class PageHeaderWidget extends AbstractWidget {
    private final Minecraft minecraft;
    private final Component title;

    public PageHeaderWidget(Minecraft minecraft, WikiPage page, int width) {
        super(0, 0, width, 30, page.commonData().title());
        this.minecraft = minecraft;
        this.title = page.commonData().title();
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int i, int i1, float v) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(this.width / 2f - minecraft.font.width(title), this.getY() + 6);
        graphics.pose().scale(2, 2);
        graphics.drawWordWrap(minecraft.font, title, 0, 0, width, -1);
        graphics.pose().popMatrix();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
