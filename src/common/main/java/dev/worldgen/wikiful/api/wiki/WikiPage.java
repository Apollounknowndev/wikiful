package dev.worldgen.wikiful.api.wiki;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.registry.WikifulBuiltInRegistries;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import dev.worldgen.wikiful.impl.wiki.page.section.WikiSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface WikiPage extends TriggerHolder {
    Codec<WikiPage> CODEC = WikifulBuiltInRegistries.WIKI_PAGE_TYPE.byNameCodec().dispatch(WikiPage::codec, Function.identity());

    CommonWikiData commonData();

    @Override
    default Optional<EventTrigger> getTrigger() {
        return this.commonData().trigger();
    }

    @Override
    default void onTriggered(ServerPlayer player, ResourceLocation id) {
        UnlockedPages.INSTANCE.add(player, id);
    }

    List<WikiSection> builtInSections();

    MapCodec<? extends WikiPage> codec();
}
