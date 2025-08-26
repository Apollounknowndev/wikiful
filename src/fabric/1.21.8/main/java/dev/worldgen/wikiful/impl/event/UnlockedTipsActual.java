package dev.worldgen.wikiful.impl.event;

import dev.worldgen.wikiful.impl.attachment.Attachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.ArrayList;
import java.util.List;

import static dev.worldgen.wikiful.impl.WikifulEntrypoint.target;


public class UnlockedTipsActual {
    @Actual
    public static void add(ServerPlayer player, ResourceLocation id) {
        List<ResourceLocation> entries = new ArrayList<>(target(player).getAttachedOrCreate(Attachments.UNLOCKED_TIPS));
        entries.add(id);
        target(player).setAttached(Attachments.UNLOCKED_TIPS, entries);
    }

    @Actual
    public static void remove(ServerPlayer player, ResourceLocation id) {
        List<ResourceLocation> entries = new ArrayList<>(target(player).getAttachedOrCreate(Attachments.UNLOCKED_TIPS));
        entries.remove(id);
        target(player).setAttached(Attachments.UNLOCKED_TIPS, entries);
    }

    @Actual
    public static boolean hasUnlocked(Player player, ResourceLocation id) {
        return target(player).getAttachedOrCreate(Attachments.UNLOCKED_TIPS).contains(id);
    }

    @Actual
    public static void clear(ServerPlayer player) {
        target(player).setAttached(Attachments.UNLOCKED_TIPS, List.of());
    }
}
