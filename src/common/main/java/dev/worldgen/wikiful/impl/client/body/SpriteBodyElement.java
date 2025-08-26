package dev.worldgen.wikiful.impl.client.body;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.body.SpriteBody;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;

public record SpriteBodyElement(SpriteBody body) implements BodyElement {
    public static SpriteBodyElement create(Body body) {
        if (body instanceof SpriteBody sprite) {
            return new SpriteBodyElement(sprite);
        }
        throw new IllegalArgumentException("[Wikiful] Tried to create sprite body element with non-sprite body");
    }

    @Override
    public int getHeight(Minecraft minecraft, int fullWidth) {
        return getHeight(fullWidth);
    }

    private int getWidth(int maxWidth) {
        return (int) ((float) body.textureWidth() * ( (float)maxWidth / (float) body.textureWidth()) * body.scale());
    }

    private int getHeight(int maxWidth) {
        return (int) ((float) body.textureHeight() * ( (float)maxWidth / (float) body.textureWidth()) * body.scale());
    }

    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int x, int y, int maxWidth) {
        int width = getWidth(maxWidth);
        int height = getHeight(maxWidth);
        x += body.centered() ? (maxWidth / 2) - (width / 2) : 0;

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, body.sprite(), x, y, width, height);

    }
}
