package dev.worldgen.wikiful.mixin.client;

import com.google.common.collect.Queues;
import dev.worldgen.wikiful.impl.client.TipToast;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Deque;

@Debug(export = true)
@Mixin(ToastComponent.class)
public abstract class ToastComponentMixin implements TipToastDuck {
    @Shadow @Final Minecraft minecraft;

    @Unique private final Deque<TipToast> queuedTips = Queues.newArrayDeque();
    @Unique @Nullable private ToastComponent.ToastInstance<TipToast> visibleTip;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderTips(GuiGraphics guiGraphics, CallbackInfo ci) {
        ToastComponent $this = ((ToastComponent) (Object) this);
        
        if (!this.minecraft.options.hideGui && visibleTip != null) {
            boolean finishedRendering = visibleTip.render(guiGraphics.guiWidth(), guiGraphics);
            if (finishedRendering) visibleTip = null;
        }
        
        // Load next queued tip if applicable
        if (!queuedTips.isEmpty() && visibleTip == null) {
            TipToast tipToast = queuedTips.removeFirst();
            visibleTip = $this.new ToastInstance<>(tipToast, 0, 0);
        }
    }

    @Inject(method = "clear", at = @At("TAIL"))
    private void clearTips(CallbackInfo ci) {
        visibleTip = null;
    }

    @Override
    public void addTip(Tip tip) {
        this.queuedTips.add(new TipToast(tip));
    }

    @Override
    public float getTipYOffset(Toast toast) {
        if (visibleTip == null || toast instanceof TipToast) return 0;
        return visibleTip.getToast().height();
    }
}
