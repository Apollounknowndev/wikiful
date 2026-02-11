package dev.worldgen.wikiful.mixin.client;

import com.google.common.collect.Queues;
import dev.worldgen.wikiful.impl.client.TipToast;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Deque;
import java.util.Set;

@Mixin(ToastManager.class)
public abstract class ToastManagerMixin implements TipToastDuck {
    @Shadow @Final Minecraft minecraft;
    @Shadow @Final private Set<SoundEvent> playedToastSounds;

    @Unique private final Deque<TipToast> queuedTips = Queues.newArrayDeque();
    @Unique @Nullable private ToastManager.ToastInstance<TipToast> visibleTip;

    @Inject(method = "update", at = @At("TAIL"))
    private void updateTips(CallbackInfo ci) {
        ToastManager $this = ((ToastManager) (Object) this);

        // Update tip visibility
        if (visibleTip != null) {
            Toast.Visibility visibility = visibleTip.visibility;
            visibleTip.update();
            if (visibleTip.visibility != visibility) {
                visibleTip.visibility.playSound(this.minecraft.getSoundManager());
            }
            if (visibleTip.hasFinishedRendering()) {
                visibleTip = null;
            }
        }

        // Load next queued tip if applicable
        if (!queuedTips.isEmpty() && (visibleTip == null || visibleTip.hasFinishedRendering())) {
            TipToast tipToast = queuedTips.removeFirst();
            visibleTip = $this.new ToastInstance<>(tipToast, 0, 0);
            SoundEvent soundEvent = tipToast.getSoundEvent();
            if (soundEvent != null && this.playedToastSounds.add(soundEvent)) {
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(soundEvent, 1.0F, 1.0F));
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderTips(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (!this.minecraft.options.hideGui && visibleTip != null) {
            visibleTip.render(guiGraphics, guiGraphics.guiWidth());
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
