package dev.worldgen.wikiful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.worldgen.wikiful.impl.client.screen.WikiPageScreen;
import dev.worldgen.wikiful.impl.client.screen.WikiSelectScreen;
import dev.worldgen.wikiful.impl.client.screen.YourProgressSubScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
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
        return operation.call(helper, openScreenButton(YOUR_PROGRESS, () -> new YourProgressSubScreen(this)));
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
        return operation.call(helper, openScreenButton(WIKI, () -> new WikiSelectScreen(this)));
    }
}
