package dev.worldgen.wikiful.impl.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class YourProgressSubScreen extends Screen {
    private static final Component TITLE = Component.translatable("menu.your_progress.title");
    private static final Component ADVANCEMENTS = Component.translatable("gui.advancements");
    private static final Component STATS = Component.translatable("gui.stats");
    public final Screen parent;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public YourProgressSubScreen(Screen screen) {
        super(TITLE);
        this.parent = screen;
    }

    @Override
    protected void init() {
        this.layout.addTitleHeader(TITLE, this.font);
        GridLayout gridLayout = this.layout.addToContents(new GridLayout());
        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);

        rowHelper.addChild(openScreenButton(this.minecraft, ADVANCEMENTS, () -> new AdvancementsScreen(this.minecraft.player.connection.getAdvancements(), this)));
        rowHelper.addChild(openScreenButton(this.minecraft, STATS, () -> new StatsScreen(this, this.minecraft.player.getStats())));


        this.layout.addToFooter(Button.builder(CommonComponents.GUI_BACK, button -> this.onClose()).width(200).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    public static Button openScreenButton(Minecraft minecraft, Component component, Supplier<Screen> supplier) {
        return Button.builder(component, button -> minecraft.setScreen(supplier.get())).width(98).build();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
