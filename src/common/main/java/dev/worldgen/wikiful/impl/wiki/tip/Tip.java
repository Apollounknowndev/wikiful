package dev.worldgen.wikiful.impl.wiki.tip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedTips;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.msrandom.multiplatform.annotations.Expect;

import java.util.List;
import java.util.Optional;

public record Tip(EventTrigger trigger, ResourceLocation sprite, int width, int padding, float displayTimeMultiplier, List<Body> body) implements TriggerHolder {
    private static final ResourceLocation DEFAULT_SPRITE = Wikiful.id("tip/default");
    public static final Codec<Tip> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        EventTrigger.CODEC.fieldOf("trigger").forGetter(Tip::trigger),
        ResourceLocation.CODEC.fieldOf("sprite").orElse(DEFAULT_SPRITE).forGetter(Tip::sprite),
        ExtraCodecs.POSITIVE_INT.fieldOf("width").orElse(240).forGetter(Tip::width),
        ExtraCodecs.NON_NEGATIVE_INT.fieldOf("padding").orElse(12).forGetter(Tip::padding),
        ExtraCodecs.POSITIVE_FLOAT.fieldOf("display_time_multiplier").orElse(1f).forGetter(Tip::displayTimeMultiplier),
        Body.LIST_CODEC.fieldOf("body").forGetter(Tip::body)
    ).apply(instance, Tip::new));

    @Override
    public Optional<EventTrigger> getTrigger() {
        return Optional.of(this.trigger);
    }

    @Override
    public void onTriggered(ServerPlayer player, ResourceLocation id) {
        UnlockedTips.INSTANCE.add(player, id);
        this.sendToast(player, id);
    }

    @Expect
    public void sendToast(ServerPlayer player, ResourceLocation id);

}
