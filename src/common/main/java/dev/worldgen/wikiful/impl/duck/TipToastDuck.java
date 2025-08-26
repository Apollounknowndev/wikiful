package dev.worldgen.wikiful.impl.duck;

import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.minecraft.client.gui.components.toasts.Toast;

public interface TipToastDuck {
    void addTip(Tip tip);

    float getTipYOffset(Toast toast);
}
