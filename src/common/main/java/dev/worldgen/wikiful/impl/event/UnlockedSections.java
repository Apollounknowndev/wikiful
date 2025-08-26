package dev.worldgen.wikiful.impl.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.msrandom.multiplatform.annotations.Expect;

public class UnlockedSections extends UnlockedInfo {
    public static final UnlockedSections INSTANCE = new UnlockedSections();

    @Expect
    public void add(ServerPlayer player, ResourceLocation id);

    @Expect
    public void remove(ServerPlayer player, ResourceLocation id);

    @Expect
    public boolean hasUnlocked(Player player, ResourceLocation id);

    @Expect
    public void clear(ServerPlayer player);
}
