package dev.worldgen.wikiful.impl.wiki.category;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public record WikiCategory(Component title, Component externalTitle, HolderSet<WikiPage> pages, HolderSet<WikiCategory> categories, boolean showIfEmpty) {
    private static final ResourceKey<WikiPage> INLINED_PAGE = ResourceKey.create(WikifulRegistries.PAGE, Wikiful.id("inlined"));
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

    public boolean visible(Player player, String search) {
        if (showIfEmpty) return true;
        for (Holder<WikiPage> page : pages) {
            if (UnlockedPages.INSTANCE.hasUnlocked(player, getId(page)) && matchesSearch(page.value().commonData().title(), search)) {
                return true;
            }
        }
        for (Holder<WikiCategory> category : categories) {
            if (category.value().visible(player, search) && matchesSearch(category.value().title(), search)) return true;
        }
        return false;
    }
    
    public static Identifier getId(Holder<WikiPage> page) {
        return page.unwrapKey().orElse(INLINED_PAGE).identifier();
    }

    private static boolean matchesSearch(Component title, String search) {
        return search.isEmpty() || title.getString().toLowerCase().contains(search);
    }
}
