package dev.worldgen.wikiful.api.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public interface BodyElement {
    int getHeight(Minecraft minecraft, int width);

    void render(GuiGraphics graphics, Minecraft minecraft, int x, int y, int width);
}
