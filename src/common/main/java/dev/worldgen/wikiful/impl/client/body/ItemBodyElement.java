package dev.worldgen.wikiful.impl.client.body;

import dev.worldgen.wikiful.api.client.BodyElement;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.body.ItemBody;
import net.minecraft.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static dev.worldgen.wikiful.impl.client.TipToast.SLOT_SPRITE;

public record ItemBodyElement(ItemBody body) implements BodyElement {
    public static ItemBodyElement create(Body body) {
        if (body instanceof ItemBody item) {
            return new ItemBodyElement(item);
        }
        throw new IllegalArgumentException("[Wikiful] Tried to create item body element with non-item body");
    }

    @Override
    public int getHeight(Minecraft minecraft, int width) {
        return 18;
    }

    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int x, int y, int width) {
        ItemStack item = body.item().get((int) (Util.getMillis() / 1000 % body.item().size()));

        x += body.centered() ? (width / 2) - 8 : 0;

        if (body.showSlot()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_SPRITE, x - 1, y - 1, 18, 18);
        }

        Player player = Minecraft.getInstance().player;
        if (player == null) {
            graphics.renderFakeItem(item, x, y, 0);
        } else {
            graphics.renderItem(player, item, x, y, 0);
        }

        if (body.showDecorations()) {
            graphics.renderItemDecorations(minecraft.font, item, x, y);
        }
    }
}
