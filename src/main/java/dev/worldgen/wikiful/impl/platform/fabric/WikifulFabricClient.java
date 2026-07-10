//? if fabric {
package dev.worldgen.wikiful.impl.platform.fabric;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.WikifulClient;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.toasts.ToastManager;

public class WikifulFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WikifulClient.init();
        KeyMappingHelper.registerKeyMapping(WikifulClient.DISMISS_TIP);
        ClientPlayNetworking.registerGlobalReceiver(DisplayTipS2C.TYPE, (packet, context) -> {
            var tips = context.player().registryAccess().lookup(WikifulRegistries.TIP);
            if (tips.isEmpty()) return;

            var tip = tips.get().get(packet.id());
            if (tip.isEmpty()) return;

            //? if >= 26.2 {
            ToastManager manager = context.client().gui.toastManager();
            //? } else {
            /*ToastManager manager = context.client().getToastManager();
            *///? }
            ((TipToastDuck)manager).addTip(tip.get().value());
        });
    }
}
//? }
