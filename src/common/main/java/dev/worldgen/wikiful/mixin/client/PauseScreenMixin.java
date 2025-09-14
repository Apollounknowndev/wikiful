package dev.worldgen.wikiful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.client.screen.WikiSelectScreen;
import dev.worldgen.wikiful.impl.client.screen.YourProgressSubScreen;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {
    protected PauseScreenMixin(Component component) {
        super(component);
    }

    @Shadow protected abstract Button openScreenButton(Component $$0, Supplier<Screen> $$1);

    @Unique private static final Component YOUR_PROGRESS = Component.translatable("gui.your_progress");
    @Unique private static final Component WIKI = Component.translatable("gui.wiki");

    @WrapOperation(
        method = "createPauseMenu",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;",
            ordinal = 0
        )
    )
    private <T extends LayoutElement> T addYourProgressButton(GridLayout.RowHelper helper, T element, Operation<T> operation) {
        if (this.minecraft.level != null && this.minecraft.player != null) {
            RegistryAccess registries = this.minecraft.level.registryAccess();
            var pages = Wikiful.filterHolders(
                registries,
                WikifulRegistries.PAGE,
                page -> page.value().getTrigger().isEmpty() || UnlockedPages.INSTANCE.hasUnlocked(this.minecraft.player, page.key().location())
            );
            if (!pages.isEmpty()) {
                return operation.call(helper, openScreenButton(YOUR_PROGRESS, () -> new YourProgressSubScreen(this)));
            }
        }
        return operation.call(helper, element);
    }


    @WrapOperation(
        method = "createPauseMenu",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;",
            ordinal = 1
        )
    )
    private <T extends LayoutElement> T addWikiButton(GridLayout.RowHelper helper, T element, Operation<T> operation) {
        if (this.minecraft.level != null && this.minecraft.player != null) {
            RegistryAccess registries = this.minecraft.level.registryAccess();
            var pages = Wikiful.filterHolders(
                registries,
                WikifulRegistries.PAGE,
                page -> page.value().getTrigger().isEmpty() || UnlockedPages.INSTANCE.hasUnlocked(this.minecraft.player, page.key().location())
            );
            if (!pages.isEmpty()) {
                Screen screen = new WikiSelectScreen(WikiSelectScreen.TITLE, this.minecraft, this, pages, Wikiful.getReferences(registries, WikifulRegistries.CATEGORY));
                return operation.call(helper, openScreenButton(WIKI, () -> screen));
            }
        }
        return operation.call(helper, element);
    }
}
