package dev.worldgen.wikiful.impl.event;

import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.Optional;

public class LootContextHelperActual {
	@Actual
	public static LootContext buildPlayerOnlyContext(ServerLevel serverLevel, Entity entity, Vec3 vec3) {
		LootParams params = new LootParams.Builder(serverLevel)
			.withParameter(LootContextParams.THIS_ENTITY, entity)
			.withParameter(LootContextParams.ORIGIN, vec3)
			.create(WikifulLootParamSets.PLAYER_ONLY);
		return new LootContext.Builder(params).create(Optional.empty());
	}
	
	@Actual
	public static LootContext buildBlockInteractionContext(ServerLevel serverLevel, Entity entity, ItemStack stack, Vec3 vec3, BlockState state) {
		LootParams params = new LootParams.Builder(serverLevel)
			.withParameter(LootContextParams.THIS_ENTITY, entity)
			.withParameter(LootContextParams.ORIGIN, vec3)
			.withParameter(LootContextParams.TOOL, stack)
			.withParameter(LootContextParams.BLOCK_STATE, state)
			.create(WikifulLootParamSets.BLOCK_INTERACTION);
		return new LootContext.Builder(params).create(Optional.empty());
	}
}
