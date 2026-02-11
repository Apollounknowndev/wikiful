package dev.worldgen.wikiful.impl.event;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.msrandom.multiplatform.annotations.Expect;

public class UnlockedTips extends UnlockedInfo {
    public static final UnlockedTips INSTANCE = new UnlockedTips();

    @Expect
    public void add(ServerPlayer player, Identifier id);

    @Expect
    public void remove(ServerPlayer player, Identifier id);

    @Expect
    public boolean hasUnlocked(Player player, Identifier id);

    @Expect
    public void clear(ServerPlayer player);
}
