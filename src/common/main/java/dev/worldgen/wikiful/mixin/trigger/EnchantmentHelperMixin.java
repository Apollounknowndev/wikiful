package dev.worldgen.wikiful.mixin.trigger;

import dev.worldgen.wikiful.impl.event.triggers.HitBlock;
import dev.worldgen.wikiful.impl.event.triggers.LocationChanged;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Inject(
        method = "onHitBlock",
        at = @At("HEAD")
    )
    private static void fireHitBlock(ServerLevel level, ItemStack stack, LivingEntity entity, Entity target, EquipmentSlot slot, Vec3 vec3, BlockState state, Consumer<Item> consumer, CallbackInfo ci) {
        if (entity instanceof ServerPlayer player) {
            HitBlock.trigger(level, player, stack, vec3, state);
        }
    }

    @Inject(
        method = "runLocationChangedEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V",
        at = @At("HEAD")
    )
    private static void fireLocationChanged(ServerLevel level, LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof ServerPlayer player) {
            LocationChanged.trigger(level, player);
        }
    }

    @Inject(
        method = "runLocationChangedEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V",
        at = @At("HEAD")
    )
    private static void fireLocationChangedWithItem(ServerLevel level, ItemStack stack, LivingEntity entity, EquipmentSlot slot, CallbackInfo ci) {
        if (entity instanceof ServerPlayer player) {
            LocationChanged.trigger(level, player);
        }
    }
}
