package dev.worldgen.wikiful.impl.client.screen;

import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.client.screen.element.BodyWidget;
import dev.worldgen.wikiful.impl.client.screen.element.PageHeaderWidget;
import dev.worldgen.wikiful.impl.client.screen.element.SectionHeaderWidget;
import dev.worldgen.wikiful.impl.client.screen.element.PageScrollableLayout;
import dev.worldgen.wikiful.impl.wiki.body.EmptyBody;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection.Visibility;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.Optional;

public class WikiPageScreen extends Screen {
    private final WikiSelectScreen parent;
    private final WikiPage page;
    private final List<Holder.Reference<WikiSection>> sections;

    protected HeaderAndFooterLayout layout;
    private PageScrollableLayout contents;

    public WikiPageScreen(WikiSelectScreen parent, WikiPage page, List<Holder.Reference<WikiSection>> sections) {
        super(page.commonData().title());
        this.parent = parent;
        this.page = page;
        this.sections = sections;
    }

    @Override
    protected void init() {
        if (this.layout != null) {
            this.layout.visitWidgets(this::removeWidget);
        }
        this.layout = new HeaderAndFooterLayout(this);
        this.layout.addToHeader(new PageHeaderWidget(minecraft, this.page, this.width));

        this.setupPages();
        this.layout.addToContents(this.contents);

        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).width(200).build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    private void setupPages() {
        LinearLayout layout = LinearLayout.vertical().spacing(2);
        layout.defaultCellSetting().alignHorizontallyLeft().alignVerticallyTop();

        int widgetWidth = this.width - 28;

        layout.addChild(BodyWidget.create(this.minecraft, new EmptyBody(4), widgetWidth));
        page.commonData().opener().forEach(entry -> layout.addChild(BodyWidget.create(this.minecraft, entry, widgetWidth)));

        for (WikiSection section : page.builtInSections()) {
            addSection(layout, widgetWidth, section, Optional.empty());
        }

        for (WikiSection section : page.commonData().sections()) {
            addSection(layout, widgetWidth, section, Optional.empty());
        }

        for (Holder.Reference<WikiSection> section : this.sections) {
            addSection(layout, widgetWidth, section.value(), section.unwrapKey());
        }

        this.contents = new PageScrollableLayout(this.minecraft, layout, this.layout.getContentHeight());
        this.contents.setMinWidth(this.width - 12);
        this.contents.setMaxHeight(this.layout.getContentHeight());
    }

    private void addSection(LinearLayout layout, int widgetWidth, WikiSection section, Optional<ResourceKey<WikiSection>> key) {
        SectionHeaderWidget header = new SectionHeaderWidget(this.minecraft, section, key, widgetWidth);
        if (header.getVisibility() == Visibility.INVISIBLE) return;

        layout.addChild(header);
        if (header.getVisibility() != Visibility.VISIBLE) return;

        section.body().forEach(entry -> layout.addChild(BodyWidget.create(this.minecraft, entry, widgetWidth)));
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
        this.parent.setInitialFocus();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int f, int g, float tickDelta) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, page.commonData().sprites().background(), 4, 31, this.width - 8, this.height - 62);
        super.render(guiGraphics, f, g, tickDelta);
    }
}
