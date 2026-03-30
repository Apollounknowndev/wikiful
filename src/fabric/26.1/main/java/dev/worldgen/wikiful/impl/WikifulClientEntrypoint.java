package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class WikifulClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WikifulClient.init();
        KeyMappingHelper.registerKeyMapping(WikifulClient.DISMISS_TIP);
        ClientPlayNetworking.registerGlobalReceiver(DisplayTipS2C.TYPE, (packet, context) -> {
            var tips = context.player().registryAccess().lookup(WikifulRegistries.TIP);
            if (tips.isEmpty()) return;

            var tip = tips.get().get(packet.id());
            if (tip.isEmpty()) return;

            ((TipToastDuck)context.client().getToastManager()).addTip(tip.get().value());
        });
    }
}
