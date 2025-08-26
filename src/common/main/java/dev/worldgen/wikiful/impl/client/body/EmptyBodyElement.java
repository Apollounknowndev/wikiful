package dev.worldgen.wikiful.impl.client.body;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.body.EmptyBody;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public record EmptyBodyElement(EmptyBody body) implements BodyElement {
    public static EmptyBodyElement create(Body body) {
        if (body instanceof EmptyBody empty) {
            return new EmptyBodyElement(empty);
        }
        throw new IllegalArgumentException("[Wikiful] Tried to create empty body element with non-empty body");
    }

    @Override
    public int getHeight(Minecraft minecraft, int width) {
        return body.spacing();
    }

    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int x, int y, int width) {

    }
}
