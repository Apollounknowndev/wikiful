package dev.worldgen.wikiful.impl.client.screen;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.api.wiki.CommonWikiData;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import dev.worldgen.wikiful.impl.wiki.category.WikiCategory;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class WikiSelectScreen extends Screen {
    public static final Component TITLE = Component.translatable("menu.wiki.title");
    private static final Identifier INWORLD_MENU_LIST_BACKGROUND = Identifier.withDefaultNamespace("textures/gui/inworld_menu_list_background.png");
    private static final ResourceKey<WikiPage> INLINED_PAGE = ResourceKey.create(WikifulRegistries.PAGE, Wikiful.id("inlined"));
    private final Screen parent;
    private final RegistryAccess registries;

    protected HeaderAndFooterLayout layout;
    protected EditBox searchBox;
    private ScrollableLayout pageButtons;
    private final List<Holder<WikiPage>> pages;
    private final List<Holder.Reference<WikiCategory>> categories;
    private final List<Holder.Reference<WikiSection>> sections;

    public WikiSelectScreen(Component title, Minecraft minecraft, Screen parent, List<Holder<WikiPage>> pages, List<Holder.Reference<WikiCategory>> categories) {
        super(title);
        this.registries = minecraft.level.registryAccess();
        this.parent = parent;
        this.pages = pages;
        this.categories = categories;
        this.sections = Wikiful.getReferences(this.registries, WikifulRegistries.SECTION);
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

    private void setupPages(String search) {
        LinearLayout linearLayout = LinearLayout.vertical().spacing(3);
        linearLayout.defaultCellSetting().alignHorizontallyCenter();

        Player player = this.minecraft.player;

        for (Holder.Reference<WikiCategory> holder : this.categories) {
            WikiCategory category = holder.value();
            if (category.visible(player, search)) {
                linearLayout.addChild(button(category.externalTitle(), new WikiSelectScreen(category.title(), minecraft, this, category.pages().stream().toList(), List.of())));
            }
        }
        for (Holder<WikiPage> page : this.pages) {
            CommonWikiData data = page.value().commonData();
            if (data.trigger().isPresent() && !UnlockedPages.INSTANCE.hasUnlocked(player, getId(page))) continue;

            String title = data.title().getString();
            if (search.isEmpty() || title.toLowerCase().contains(search)) {
                linearLayout.addChild(button(data.title(), new WikiPageScreen(this, page.value(), collectSections(page))));
            }
        }

        this.pageButtons = new ScrollableLayout(this.minecraft, linearLayout, this.layout.getContentHeight());
        this.pageButtons.setMaxHeight(this.layout.getContentHeight() - 19);
    }

    public static Identifier getId(Holder<WikiPage> page) {
        return page.unwrapKey().orElse(INLINED_PAGE).identifier();
    }

    private Button button(Component title, Screen screen) {
        return Button.builder(title, button -> this.minecraft.setScreen(screen)).width(200).build();
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
