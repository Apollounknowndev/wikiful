package dev.worldgen.wikiful.impl.wiki.page.section;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.api.wiki.WikiPage;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import dev.worldgen.wikiful.impl.event.UnlockedSections;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;

import java.util.List;
import java.util.Optional;

public record WikiSection(Optional<Holder<WikiPage>> parent, Optional<EventTrigger> trigger, Visibility visibility, Component title, List<Body> body) implements TriggerHolder {
    private static final Codec<WikiSection> CODEC = RecordCodecBuilder.<WikiSection>create(instance -> instance.group(
        RegistryFixedCodec.create(WikifulRegistries.PAGE).optionalFieldOf("parent").forGetter(WikiSection::parent),
        EventTrigger.CODEC.optionalFieldOf("trigger").forGetter(WikiSection::trigger),
        Visibility.CODEC.fieldOf("visibility").orElse(Visibility.VISIBLE).forGetter(WikiSection::visibility),
        ComponentSerialization.CODEC.fieldOf("title").forGetter(WikiSection::title),
        Body.FULL_CODEC.listOf().fieldOf("body").forGetter(WikiSection::body)
    ).apply(instance, WikiSection::new)).validate(WikiSection::validate);
    public static final Codec<WikiSection> STANDALONE_CODEC = CODEC.validate(WikiSection::validateStandalone);
    public static final Codec<WikiSection> PAGE_CODEC = CODEC.validate(WikiSection::validatePage);

    private static DataResult<WikiSection> validate(WikiSection section) {
        boolean visible = section.visibility == Visibility.VISIBLE;
        boolean hasTrigger = section.trigger.isPresent();
        if (visible == hasTrigger) {
            if (visible) {
                return DataResult.error(() -> "Wiki section with trigger should be locked or invisible");
            }
            return DataResult.error(() -> "Wiki section locked or invisible should have a trigger");
        }
        return DataResult.success(section);
    }

    private static DataResult<WikiSection> validatePage(WikiSection section) {
        if (section.parent().isPresent()) {
            return DataResult.error(() -> "Wiki section inside of page should not have parent");
        }
        if (section.visibility != Visibility.VISIBLE) {
            return DataResult.error(() -> "Wiki section inside of page should always be visible");
        }
        return DataResult.success(section);
    }

    private static DataResult<WikiSection> validateStandalone(WikiSection section) {
        if (section.parent().isEmpty()) {
            return DataResult.error(() -> "Standalone wiki section should have parent");
        }
        return DataResult.success(section);
    }


    @Override
    public Optional<EventTrigger> getTrigger() {
        return this.trigger;
    }

    @Override
    public void onTriggered(ServerPlayer player, ResourceLocation id) {
        UnlockedSections.INSTANCE.add(player, id);
    }

    public enum Visibility implements StringRepresentable {
        VISIBLE("visible"),
        LOCKED_CONTENTS("locked_contents"),
        LOCKED_ALL("locked_all"),
        INVISIBLE("invisible");

        public static final Codec<Visibility> CODEC = StringRepresentable.fromEnum(Visibility::values);

        private final String name;

        Visibility(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
