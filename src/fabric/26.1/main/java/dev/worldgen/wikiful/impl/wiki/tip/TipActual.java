package dev.worldgen.wikiful.impl.wiki.tip;

import dev.worldgen.wikiful.impl.network.DisplayTipS2C;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.msrandom.multiplatform.annotations.Actual;

public class TipActual {
    @Actual
    private void sendToast(ServerPlayer player, Identifier id) {
        ServerPlayNetworking.send(player, new DisplayTipS2C(id));
    }
}
