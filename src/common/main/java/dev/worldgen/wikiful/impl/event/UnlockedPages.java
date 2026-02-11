package dev.worldgen.wikiful.impl.event;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.msrandom.multiplatform.annotations.Expect;

public class UnlockedPages extends UnlockedInfo {
    public static final UnlockedPages INSTANCE = new UnlockedPages();

    @Expect
    public void add(ServerPlayer player, Identifier id);

    @Expect
    public void remove(ServerPlayer player, Identifier id);

    @Expect
    public boolean hasUnlocked(Player player, Identifier id);

    @Expect
    public void clear(ServerPlayer player);
}
