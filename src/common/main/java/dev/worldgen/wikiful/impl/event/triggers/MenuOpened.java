package dev.worldgen.wikiful.impl.event.triggers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.wikiful.api.event.EventTrigger;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.registry.WikifulEventTriggerTypes;
import dev.worldgen.wikiful.impl.registry.WikifulLootParamSets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record MenuOpened(Optional<LootItemCondition> condition, ResourceKey<MenuType<?>> menu) implements EventTrigger {
    public static final MapCodec<MenuOpened> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        EventTrigger.conditionCodec(WikifulLootParamSets.PLAYER_ONLY).forGetter(MenuOpened::condition),
        ResourceKey.codec(Registries.MENU).fieldOf("menu").forGetter(MenuOpened::menu)
    ).apply(instance, MenuOpened::new));

    public static void trigger(ServerLevel level, ServerPlayer player, MenuType<?> menu) {
        LootContext context = context(level, player, player.position());
        WikifulEventTriggerTypes.MENU_OPENED.onListeners(player, trigger -> trigger instanceof MenuOpened menuOpened && menuOpened.matches(context, menu));
    }

    private boolean matches(LootContext context, MenuType<?> menu) {
        if (!this.condition.map(predicate -> predicate.test(context)).orElse(true)) return false;
        return BuiltInRegistries.MENU.getResourceKey(menu).map(this.menu::equals).orElse(false);
    }

    private static LootContext context(ServerLevel serverLevel, Entity entity, Vec3 vec3) {
        LootParams params = new LootParams.Builder(serverLevel)
            .withParameter(LootContextParams.THIS_ENTITY, entity)
            .withParameter(LootContextParams.ORIGIN, vec3)
            .create(WikifulLootParamSets.PLAYER_ONLY);
        return new LootContext.Builder(params).create(Optional.empty());
    }

    @Override
    public EventTriggerType<? extends EventTrigger> type() {
        return WikifulEventTriggerTypes.MENU_OPENED;
    }
}
