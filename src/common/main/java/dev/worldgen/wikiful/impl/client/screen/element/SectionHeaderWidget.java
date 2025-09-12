package dev.worldgen.wikiful.impl.client.screen.element;

import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedSections;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection.Visibility;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class SectionHeaderWidget extends AbstractWidget {
    public static final Component UNKNOWN_TITLE = Component.translatable("wikiful.unknown").withStyle(ChatFormatting.GRAY);
    public static final ResourceLocation LOCKED_SPRITE = Wikiful.id("page/lock");
    private final Minecraft minecraft;
    private final Component title;
    private final Visibility visibility;
    private final int color;

    public SectionHeaderWidget(Minecraft minecraft, WikiSection section, Optional<ResourceKey<WikiSection>> key, int width) {
        super(0, 0, width, 30, section.title());
        this.minecraft = minecraft;
        this.visibility = getVisibility(section, key, minecraft);

        Component baseTitle = section.title();
        switch (this.visibility) {
            case LOCKED_CONTENTS -> {
                this.title = baseTitle.copy().withStyle(ChatFormatting.GRAY);
                this.color = 0xFFA0A0A0;
                this.setTooltip(Tooltip.create(Component.translatable("wikiful.section_locked")));
            }
            case LOCKED_ALL -> {
                this.title = UNKNOWN_TITLE;
                this.color = 0xFFA0A0A0;
                this.setTooltip(Tooltip.create(Component.translatable("wikiful.section_locked")));
            }
            default -> {
                this.title = baseTitle;
                this.color = 0xFFFFFFFF;
            }
        }
    }

    private Visibility getVisibility(WikiSection holder, Optional<ResourceKey<WikiSection>> key, Minecraft minecraft) {
        Visibility sectionVisibility = holder.visibility();
        if (sectionVisibility.equals(Visibility.VISIBLE)) {
            return Visibility.VISIBLE;
        }
        if (minecraft.player == null) {
            return sectionVisibility;
        }
        boolean unlocked = key.map(t -> UnlockedSections.INSTANCE.hasUnlocked(minecraft.player, t.location())).orElse(true);
        return unlocked ? Visibility.VISIBLE : sectionVisibility;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int i, int i1, float v) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(this.getX(), this.getY() + 6);
        graphics.pose().scale(2, 2);
        int textX = 0;
        if (this.visibility != Visibility.VISIBLE) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, LOCKED_SPRITE, 0, 0, 8, 8);
            textX = 12;
        }
        graphics.drawWordWrap(minecraft.font, title, textX, 0, width, -1);
        graphics.pose().popMatrix();

        int lineY = this.getY() + 24;
        graphics.fill(this.getX(), lineY, this.getX() + width, lineY + 2, this.color);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
