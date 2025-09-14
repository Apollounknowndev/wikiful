package dev.worldgen.wikiful.mixin.trigger;

import dev.worldgen.wikiful.impl.event.triggers.DestroyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(
        method = "mineBlock",
        at = @At("HEAD")
    )
    private void fireDestroyBlock(Level level, BlockState state, BlockPos pos, Player player, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            DestroyBlock.trigger(serverLevel, serverPlayer, ((ItemStack)(Object)this), Vec3.atCenterOf(pos), state);
        }
    }
}
