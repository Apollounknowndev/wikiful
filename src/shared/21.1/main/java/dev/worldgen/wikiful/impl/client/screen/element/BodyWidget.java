package dev.worldgen.wikiful.impl.client.screen.element;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.api.client.BodyElementRegistry;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;

public class BodyWidget extends AbstractWidget {
    private final Minecraft minecraft;
    private final BodyElement element;

    public BodyWidget(Minecraft minecraft, BodyElement element, int width) {
        super(0, 0, width, element.getHeight(minecraft, width), CommonComponents.EMPTY);
        this.minecraft = minecraft;
        this.element = element;
    }

    public static BodyWidget create(Minecraft minecraft, Body body, int width) {
        return new BodyWidget(minecraft, BodyElementRegistry.createElement(body), width);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        this.element.render(guiGraphics, minecraft, this.getX(), this.getY(), this.getWidth());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
