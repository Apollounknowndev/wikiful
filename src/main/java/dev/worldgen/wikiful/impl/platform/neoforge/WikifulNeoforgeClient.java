//? if neoforge {
/*package dev.worldgen.wikiful.impl.platform.neoforge;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.WikifulClient;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@Mod(value = Wikiful.MOD_ID, dist = Dist.CLIENT)
public class WikifulNeoforgeClient {
    public WikifulNeoforgeClient(IEventBus bus) {
        WikifulClient.init();
        bus.addListener(WikifulNeoforgeClient::registerPacketHandler);
        bus.addListener(WikifulNeoforgeClient::registerKeybinds);
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
            
            //? if >= 26.2 {
            ToastManager manager = Minecraft.getInstance().gui.toastManager();
            //? } else {
            /^ToastManager manager = Minecraft.getInstance().getToastManager();
            ^///? }
            ((TipToastDuck)manager).addTip(tip.get().value());
        });
    }
}
*///? }
