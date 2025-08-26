package dev.worldgen.wikiful.impl.client.screen;

import dev.worldgen.wikiful.api.registry.WikifulRegistryKeys;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class WikiSelectScreen extends Screen {
    private static final ResourceLocation INWORLD_MENU_LIST_BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/inworld_menu_list_background.png");
    private static final Component TITLE = Component.translatable("menu.wiki.title");
    private final Screen parent;

    protected HeaderAndFooterLayout layout;
    protected EditBox searchBox;
    private ScrollableLayout pageButtons;
    private List<Holder.Reference<WikiPage>> pages;
    private List<Holder.Reference<WikiSection>> sections;

    public WikiSelectScreen(Screen parent) {
        super(TITLE);
        this.parent = parent;
    }

    @Override
    protected void init() {
        if (this.layout != null) {
            this.layout.visitWidgets(this::removeWidget);
        }
        this.layout = new HeaderAndFooterLayout(this);
        this.layout.addTitleHeader(this.title, this.font);

        if (this.searchBox == null) {
            this.searchBox = new EditBox(this.font, this.width / 2 - 100, 25, 200, 20, this.searchBox, Component.translatable("selectWorld.search"));
            this.searchBox.setResponder(string -> this.init());
            this.addWidget(this.searchBox);
        }

        this.setupPages(this.searchBox.getValue().toLowerCase());
        this.layout.addToContents(this.pageButtons);

        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).width(200).build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    private void setupPages(String string) {
        LinearLayout linearLayout = LinearLayout.vertical().spacing(3);
        linearLayout.defaultCellSetting().alignHorizontallyCenter();
        if (this.minecraft.level != null && this.minecraft.player != null) {
            if (this.pages == null) {
                this.pages = this.minecraft.level.registryAccess().lookupOrThrow(WikifulRegistryKeys.WIKI_PAGE).listElements().toList();
                this.sections = this.minecraft.level.registryAccess().lookupOrThrow(WikifulRegistryKeys.WIKI_SECTION).listElements().toList();
            }
            for (Holder.Reference<WikiPage> page : this.pages) {
                if (page.value().commonData().trigger().isPresent() && !UnlockedPages.INSTANCE.hasUnlocked(this.minecraft.player, page.key().location())) continue;

                String title = page.value().commonData().title().getString();
                if (string.isEmpty() || title.toLowerCase().contains(string)) {
                    linearLayout.addChild(Button.builder(page.value().commonData().title(), button -> {
                        this.minecraft.setScreen(new WikiPageScreen(this, page.value(), collectSections(page)));
                    }).width(200).build());
                }
            }
        }
        this.pageButtons = new ScrollableLayout(this.minecraft, linearLayout, this.layout.getContentHeight());
        this.pageButtons.setMaxHeight(this.layout.getContentHeight() - 19);
    }

    private List<Holder.Reference<WikiSection>> collectSections(Holder<WikiPage> page) {
        return this.sections.stream().filter(section -> section.value().parent().map(holder -> holder.is(page)).orElse(false)).toList();
    }

    @Override
    public void setInitialFocus() {
        this.setInitialFocus(this.searchBox);
    }

    @Override
    protected void repositionElements() {
        this.init();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Screen.INWORLD_HEADER_SEPARATOR, 0, 50, 0.0F, 0.0F, this.width, 2, 32, 2);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Screen.INWORLD_FOOTER_SEPARATOR, 0, this.height - 33, 0.0F, 0.0F, this.width, 2, 32, 2);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, INWORLD_MENU_LIST_BACKGROUND, 0, 51, 0.0F, 0.0F, this.width, this.height - 84  , 32, 32);

        super.render(guiGraphics, i, j, f);
        this.searchBox.render(guiGraphics, i, j, f);
    }
}
