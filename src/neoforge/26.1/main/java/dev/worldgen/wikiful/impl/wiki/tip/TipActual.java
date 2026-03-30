package dev.worldgen.wikiful.impl.wiki.tip;

import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.msrandom.multiplatform.annotations.Actual;
import net.neoforged.neoforge.network.PacketDistributor;

public class TipActual {
    @Actual
    private void sendToast(ServerPlayer player, Identifier id) {
        PacketDistributor.sendToPlayer(player, new DisplayTipS2C(id));
    }
}
