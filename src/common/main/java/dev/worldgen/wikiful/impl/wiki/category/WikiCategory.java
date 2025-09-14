package dev.worldgen.wikiful.impl.wiki.category;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.client.screen.WikiSelectScreen;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

import java.util.Optional;

public record WikiCategory(Component title, Component externalTitle, HolderSet<WikiPage> pages, HolderSet<WikiCategory> categories, boolean showIfEmpty) {
    public static final Codec<WikiCategory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ComponentSerialization.CODEC.fieldOf("title").forGetter(WikiCategory::title),
        ComponentSerialization.CODEC.optionalFieldOf("external_title").forGetter(category -> Optional.of(category.externalTitle)),
        RegistryCodecs.homogeneousList(WikifulRegistries.PAGE).fieldOf("pages").orElse(HolderSet.empty()).forGetter(WikiCategory::pages),
        RegistryCodecs.homogeneousList(WikifulRegistries.CATEGORY).fieldOf("categories").orElse(HolderSet.empty()).forGetter(WikiCategory::categories),
        Codec.BOOL.fieldOf("show_if_empty").forGetter(WikiCategory::showIfEmpty)
    ).apply(instance, WikiCategory::new));

    public WikiCategory(Component title, Optional<Component> externalTitle, HolderSet<WikiPage> pages, HolderSet<WikiCategory> categories, boolean showIfEmpty) {
        this(title, externalTitle.orElse(title), pages, categories, showIfEmpty);
    }

    public boolean visible(LocalPlayer player, String search) {
        if (showIfEmpty) return true;
        for (Holder<WikiPage> page : pages) {
            if (UnlockedPages.INSTANCE.hasUnlocked(player, WikiSelectScreen.getId(page)) && matchesSearch(page.value().commonData().title(), search)) {
                return true;
            }
        }
        for (Holder<WikiCategory> category : categories) {
            if (category.value().visible(player, search) && matchesSearch(category.value().title(), search)) return true;
        }
        return false;
    }

    private static boolean matchesSearch(Component title, String search) {
        return search.isEmpty() || title.getString().toLowerCase().contains(search);
    }
}
