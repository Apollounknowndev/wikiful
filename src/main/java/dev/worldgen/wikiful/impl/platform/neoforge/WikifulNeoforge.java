//? if neoforge {
/*package dev.worldgen.wikiful.impl.platform.neoforge;

import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.api.wiki.page.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.WikifulEvents;
import dev.worldgen.wikiful.impl.command.WikifulCommand;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import dev.worldgen.wikiful.impl.wiki.category.WikiCategory;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.minecraft.core.Registry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.*;

import java.util.*;

@Mod(Wikiful.MOD_ID)
public class WikifulNeoforge {
    public static final List<Registry<?>> REGISTRIES = new ArrayList<>();
    
    public WikifulNeoforge(IEventBus bus) {
        Wikiful.init();
        WikifulAttachments.init();
        Wikiful.REGISTRAR.registerAll();

        bus.addListener(WikifulNeoforge::registerStaticRegistries);
        bus.addListener(WikifulNeoforge::registerDatapackRegistries);
        bus.addListener(WikifulNeoforge::registerPayloadTypes);
        NeoForge.EVENT_BUS.addListener(WikifulNeoforge::registerCommands);
        NeoForge.EVENT_BUS.addListener(WikifulNeoforge::onServerStarting);
    }
    
    public static void registerStaticRegistries(NewRegistryEvent event) {
        for (Registry<?> registry : REGISTRIES) {
            event.register(registry);
        }
    }

    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(WikifulRegistries.TIP, Tip.CODEC, Tip.CODEC);
        event.dataPackRegistry(WikifulRegistries.PAGE, WikiPage.CODEC, WikiPage.CODEC);
        event.dataPackRegistry(WikifulRegistries.SECTION, WikiSection.STANDALONE_CODEC, WikiSection.STANDALONE_CODEC);
        event.dataPackRegistry(WikifulRegistries.CATEGORY, WikiCategory.CODEC, WikiCategory.CODEC);
    }

    public static void registerPayloadTypes(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(DisplayTipS2C.TYPE, DisplayTipS2C.CODEC);
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        WikifulCommand.register(event.getDispatcher(), event.getBuildContext());
    }

    public static void onServerStarting(ServerStartingEvent event) {
        WikifulEvents.onRegistryLoad(event.getServer().registryAccess());
    }
}
*///? }
