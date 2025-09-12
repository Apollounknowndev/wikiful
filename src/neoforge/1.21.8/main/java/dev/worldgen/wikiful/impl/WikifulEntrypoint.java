package dev.worldgen.wikiful.impl;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.api.registry.WikifulRegistryKeys;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.attachment.Attachments;
import dev.worldgen.wikiful.impl.command.WikifulCommand;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

@Mod(Wikiful.MOD_ID)
public class WikifulEntrypoint {
    public static final DeferredRegister<EventTriggerType<?>> DEFERRED_TRIGGER_TYPES = DeferredRegister.create(WikifulRegistryKeys.EVENT_TRIGGER_TYPE, Wikiful.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends Body>> DEFERRED_BODY_TYPES = DeferredRegister.create(WikifulRegistryKeys.BODY_TYPE, Wikiful.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends WikiPage>> DEFERRED_WIKI_PAGE_TYPES = DeferredRegister.create(WikifulRegistryKeys.WIKI_PAGE_TYPE, Wikiful.MOD_ID);

    public static final Map<String, EventTriggerType<?>> TRIGGER_TYPES = new HashMap<>();
    public static final Map<String, MapCodec<? extends Body>> BODY_TYPES = new HashMap<>();
    public static final Map<String, MapCodec<? extends WikiPage>> WIKI_PAGE_TYPES = new HashMap<>();
    public static final Map<String, AttachmentType<?>> ATTACHMENT_TYPES = new HashMap<>();

    public WikifulEntrypoint(IEventBus bus) {
        Wikiful.init();
        Attachments.init();

        bus.addListener(this::registerDatapackRegistries);
        this.registerAllTheThings(bus);
        bus.addListener(this::registerAttachments);
        bus.addListener(this::registerPayloadTypes);
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
    }

    private void registerAllTheThings(IEventBus bus) {
        TRIGGER_TYPES.forEach((name, type) -> DEFERRED_TRIGGER_TYPES.register(name, () -> type));
        DEFERRED_TRIGGER_TYPES.register(bus);

        BODY_TYPES.forEach((name, codec) -> DEFERRED_BODY_TYPES.register(name, () -> codec));
        DEFERRED_BODY_TYPES.register(bus);

        WIKI_PAGE_TYPES.forEach((name, codec) -> DEFERRED_WIKI_PAGE_TYPES.register(name, () -> codec));
        DEFERRED_WIKI_PAGE_TYPES.register(bus);
    }

    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(WikifulRegistryKeys.TIP, Tip.CODEC, Tip.CODEC);
        event.dataPackRegistry(WikifulRegistryKeys.WIKI_PAGE, WikiPage.CODEC, WikiPage.CODEC);
        event.dataPackRegistry(WikifulRegistryKeys.WIKI_SECTION, WikiSection.STANDALONE_CODEC, WikiSection.STANDALONE_CODEC);
    }

    public void registerAttachments(RegisterEvent event) {
        event.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, helper -> ATTACHMENT_TYPES.forEach((name, type) -> helper.register(Wikiful.id(name), type)));
    }

    public void registerPayloadTypes(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(DisplayTipS2C.TYPE, DisplayTipS2C.CODEC);
    }

    public void registerCommands(RegisterCommandsEvent event) {
        WikifulCommand.register(event.getDispatcher(), event.getBuildContext());
    }

    public void onServerStarting(ServerStartingEvent event) {
        WikifulEvents.onRegistryLoad(event.getServer().registryAccess());
    }
}
