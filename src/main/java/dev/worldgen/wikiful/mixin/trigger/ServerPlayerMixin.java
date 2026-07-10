package dev.worldgen.wikiful.mixin.trigger;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.worldgen.wikiful.impl.event.triggers.MenuOpened;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @WrapOperation(
        method = "openMenu",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;initMenu(Lnet/minecraft/world/inventory/AbstractContainerMenu;)V"
        )
    )
    private void fireMenuOpened(ServerPlayer player, AbstractContainerMenu menu, Operation<Void> operation) {
        MenuOpened.trigger((ServerLevel) player.level(), player, menu.getType());
        operation.call(player, menu);
    }
}
