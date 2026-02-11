package dev.worldgen.wikiful.impl.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.msrandom.multiplatform.annotations.Expect;

public class LootContextHelper {
	@Expect
	public static LootContext buildPlayerOnlyContext(ServerLevel serverLevel, Entity entity, Vec3 vec3);
	
	@Expect
	public static LootContext buildBlockInteractionContext(ServerLevel serverLevel, Entity entity, ItemStack stack, Vec3 vec3, BlockState state);
}
