package dev.worldgen.wikiful.api.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface BodyElement {
    int getHeight(Minecraft minecraft, int width);

    void extractElementRenderState(GuiGraphicsExtractor graphics, Minecraft minecraft, int x, int y, int width);
}
