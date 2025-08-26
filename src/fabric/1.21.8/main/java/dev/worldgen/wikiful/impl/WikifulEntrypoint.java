package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.registry.WikifulRegistryKeys;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.attachment.Attachments;
import dev.worldgen.wikiful.impl.command.WikifulCommand;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import dev.worldgen.wikiful.impl.wiki.tip.Tip;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.world.entity.player.Player;

public class WikifulEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        Wikiful.init();
        Attachments.init();

        DynamicRegistries.registerSynced(WikifulRegistryKeys.TIP, Tip.CODEC);
        DynamicRegistries.registerSynced(WikifulRegistryKeys.WIKI_PAGE, WikiPage.CODEC);
        DynamicRegistries.registerSynced(WikifulRegistryKeys.WIKI_SECTION, WikiSection.STANDALONE_CODEC);

        PayloadTypeRegistry.playS2C().register(DisplayTipS2C.TYPE, DisplayTipS2C.CODEC);

        ServerLifecycleEvents.SERVER_STARTING.register(server -> WikifulEvents.onRegistryLoad(server.registryAccess()));
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> WikifulCommand.register(dispatcher, buildContext));
    }


    public static AttachmentTarget target(Player player) {
        return (AttachmentTarget) player;
    }
}
