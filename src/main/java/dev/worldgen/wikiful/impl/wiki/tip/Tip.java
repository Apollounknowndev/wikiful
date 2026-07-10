package dev.worldgen.wikiful.impl.wiki.tip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.event.TriggerHolder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedInfo;
import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.impl.wiki.body.TextBody;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.Optional;

//? if fabric
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//? if neoforge
//import net.neoforged.neoforge.network.PacketDistributor;

public record Tip(EventTrigger trigger, Identifier sprite, int width, int padding, float displayTimeMultiplier, boolean cannotDismiss, List<Body> body) implements TriggerHolder {
    public static final Identifier DEFAULT_SPRITE = Wikiful.id("tip/default");
    public static final Body DISMISS_BODY = new TextBody(Component.translatable("tip.wikiful.dismiss", Component.keybind("key.wikiful.dismiss_tip")).withStyle(ChatFormatting.GRAY));
    public static final Codec<Tip> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        EventTrigger.CODEC.fieldOf("trigger").forGetter(Tip::trigger),
        Identifier.CODEC.fieldOf("sprite").orElse(DEFAULT_SPRITE).forGetter(Tip::sprite),
        ExtraCodecs.POSITIVE_INT.fieldOf("width").orElse(240).forGetter(Tip::width),
        ExtraCodecs.NON_NEGATIVE_INT.fieldOf("padding").orElse(12).forGetter(Tip::padding),
        ExtraCodecs.POSITIVE_FLOAT.fieldOf("display_time_multiplier").orElse(1f).forGetter(Tip::displayTimeMultiplier),
        Codec.BOOL.optionalFieldOf("cannot_dismiss", false).forGetter(Tip::cannotDismiss),
        Body.LIST_CODEC.fieldOf("body").forGetter(Tip::body)
    ).apply(instance, Tip::new));

    @Override
    public Optional<EventTrigger> getTrigger() {
        return Optional.of(this.trigger);
    }

    @Override
    public void onTriggered(ServerPlayer player, Identifier id) {
        UnlockedInfo.addUnlock(WikifulRegistries.TIP, player, id);
        this.sendToast(player, id);
    }

    public void sendToast(ServerPlayer player, Identifier id) {
        //? if fabric
        ServerPlayNetworking.send(player, new DisplayTipS2C(id));
        //? if neoforge
        //PacketDistributor.sendToPlayer(player, new DisplayTipS2C(id));
    }

}
