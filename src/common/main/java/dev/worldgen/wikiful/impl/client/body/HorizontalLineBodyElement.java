package dev.worldgen.wikiful.impl.client.body;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.body.EmptyBody;
import dev.worldgen.wikiful.impl.wiki.body.HorizontalLineBody;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public record HorizontalLineBodyElement(HorizontalLineBody body) implements BodyElement {
    public static HorizontalLineBodyElement create(Body body) {
        if (body instanceof HorizontalLineBody line) {
            return new HorizontalLineBodyElement(line);
        }
        throw new IllegalArgumentException("[Wikiful] Tried to create horizontal line body element with non-horizontal line body");
    }

    @Override
    public int getHeight(Minecraft minecraft, int width) {
        return body.spacing() * 2 + body().height();
    }

    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int x, int y, int width) {
        y += body.spacing();
        graphics.fill(x, y, x + width, y + body.height() - 1, body.color().rgba());
    }
}
