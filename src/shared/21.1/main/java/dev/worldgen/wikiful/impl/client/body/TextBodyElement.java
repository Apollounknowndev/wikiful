package dev.worldgen.wikiful.impl.client.body;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.body.TextBody;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FormattedCharSequence;

public record TextBodyElement(TextBody body) implements BodyElement {
    public static TextBodyElement create(Body body) {
        if (body instanceof TextBody text) {
            return new TextBodyElement(text);
        }
        throw new IllegalArgumentException("[Wikiful] Tried to create text body element with non-text body");
    }

    @Override
    public int getHeight(Minecraft minecraft, int width) {
        return minecraft.font.split(body.contents(), width).size() * 9;
    }

    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int x, int y, int width) {
        Font font = minecraft.font;
        for (FormattedCharSequence line : font.split(body.contents(), width)) {
            graphics.drawString(font, line, x, y, -1, true);
            y += font.lineHeight;
        }
    }
}
