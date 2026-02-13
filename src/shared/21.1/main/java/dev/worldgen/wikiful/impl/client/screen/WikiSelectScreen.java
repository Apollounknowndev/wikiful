package dev.worldgen.wikiful.impl.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.api.wiki.CommonWikiData;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import dev.worldgen.wikiful.impl.wiki.category.WikiCategory;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
    private PageList buttonList;
    
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

        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).width(200).build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    private void setupPages(String search) {
        this.buttonList = layout.addToContents(new PageList(minecraft, width));
        Player player = this.minecraft.player;

        for (Holder.Reference<WikiCategory> holder : this.categories) {
            WikiCategory category = holder.value();
            if (category.visible(player, search)) {
                buttonList.addEntry(button(category.externalTitle(), new WikiSelectScreen(category.title(), minecraft, this, category.pages().stream().toList(), List.of())));
            }
        }
        for (Holder<WikiPage> page : this.pages) {
            CommonWikiData data = page.value().commonData();
            if (data.trigger().isPresent() && !UnlockedPages.INSTANCE.hasUnlocked(player, getId(page))) continue;

            String title = data.title().getString();
            if (search.isEmpty() || title.toLowerCase().contains(search)) {
                buttonList.addEntry(button(data.title(), new WikiPageScreen(this, page.value(), collectSections(page))));
            }
        }
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
    public void render(GuiGraphics guiGraphics, int x, int y, float f) {
        super.render(guiGraphics, x, y, f);
        this.searchBox.render(guiGraphics, x, y, f);
    }
    
    public class PageList extends ContainerObjectSelectionList<PageList.Entry> {
        public PageList(Minecraft minecraft, int width) {
            super(minecraft, width, WikiSelectScreen.this.layout.getContentHeight() - 25, 25, 25);
        }
        
        public void addEntry(AbstractWidget widget) {
            widget.setX(width / 2 - widget.getWidth() / 2);
            this.addEntry(new Entry(widget));
        }
        
        public void updateSize(int width, HeaderAndFooterLayout layout) {
            super.updateSize(width, layout);
            this.children().forEach(entry -> entry.widget.setX(width / 2 - entry.widget.getWidth() / 2));
        }
        
        static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
            final AbstractWidget widget;
            
            Entry(AbstractWidget widget) {
                this.widget = widget;
            }
            
            public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
                this.widget.setY(top);
                this.widget.render(guiGraphics, mouseX, mouseY, partialTick);
            }
            
            public List<? extends GuiEventListener> children() {
                return List.of(this.widget);
            }
            
            public List<? extends NarratableEntry> narratables() {
                return List.of(this.widget);
            }
        }
    }
}
