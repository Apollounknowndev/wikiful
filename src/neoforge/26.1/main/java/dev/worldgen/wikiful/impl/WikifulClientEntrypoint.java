package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@Mod(value = Wikiful.MOD_ID, dist = Dist.CLIENT)
public class WikifulClientEntrypoint {
    public WikifulClientEntrypoint(IEventBus bus) {
        WikifulClient.init();
        bus.addListener(WikifulClientEntrypoint::registerPacketHandler);
        bus.addListener(WikifulClientEntrypoint::registerKeybinds);
    }
    
    private static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.registerCategory(WikifulClient.CATEGORY);
        event.register(WikifulClient.DISMISS_TIP);
    }
    
    private static void registerPacketHandler(RegisterClientPayloadHandlersEvent event) {
        event.register(DisplayTipS2C.TYPE, (packet, context) -> {
            var tips = context.player().registryAccess().lookup(WikifulRegistries.TIP);
            if (tips.isEmpty()) return;

            var tip = tips.get().get(packet.id());
            if (tip.isEmpty()) return;

            ((TipToastDuck)Minecraft.getInstance().getToastManager()).addTip(tip.get().value());
        });
    }
}
