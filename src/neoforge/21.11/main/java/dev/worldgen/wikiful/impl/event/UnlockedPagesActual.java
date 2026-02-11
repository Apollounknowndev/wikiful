package dev.worldgen.wikiful.impl.event;

import dev.worldgen.wikiful.impl.attachment.Attachments;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.ArrayList;
import java.util.List;


public class UnlockedPagesActual {
    @Actual
    public static void add(ServerPlayer player, Identifier id) {
        List<Identifier> entries = new ArrayList<>(player.getData(Attachments.UNLOCKED_PAGES));
        entries.add(id);
        player.setData(Attachments.UNLOCKED_PAGES, entries);
    }

    @Actual
    public static void remove(ServerPlayer player, Identifier id) {
        List<Identifier> entries = new ArrayList<>(player.getData(Attachments.UNLOCKED_PAGES));
        entries.remove(id);
        player.setData(Attachments.UNLOCKED_PAGES, entries);
    }

    @Actual
    public static boolean hasUnlocked(Player player, Identifier id) {
        return player.getData(Attachments.UNLOCKED_PAGES).contains(id);
    }

    @Actual
    public static void clear(ServerPlayer player) {
        player.setData(Attachments.UNLOCKED_PAGES, List.of());
    }
}
