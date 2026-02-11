package dev.worldgen.wikiful.impl.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.worldgen.wikiful.api.registry.WikifulRegistries;
import dev.worldgen.wikiful.impl.event.UnlockedInfo;
import dev.worldgen.wikiful.impl.event.UnlockedPages;
import dev.worldgen.wikiful.impl.event.UnlockedSections;
import dev.worldgen.wikiful.impl.event.UnlockedTips;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class WikifulCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(
            literal("wikiful").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(createSubcommand(buildContext, "page", WikifulRegistries.PAGE, UnlockedPages.INSTANCE))
                .then(createSubcommand(buildContext, "section", WikifulRegistries.SECTION, UnlockedSections.INSTANCE))
                .then(createSubcommand(buildContext, "tip", WikifulRegistries.TIP, UnlockedTips.INSTANCE))
        );
    }

    private static <T> LiteralArgumentBuilder<CommandSourceStack> createSubcommand(CommandBuildContext buildContext, String name, ResourceKey<Registry<T>> key, UnlockedInfo info) {
        return literal(name).requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS)).then(argument("target", EntityArgument.player())
            .then(literal("unlock")
                .then(argument("id", ResourceArgument.resource(buildContext, key))
                    .executes(context -> unlock(context.getSource(), name, info, EntityArgument.getPlayer(context, "target"), key, Optional.of(ResourceArgument.getResource(context, "id", key))))
                )
                .then(literal("*")
                    .executes(context -> unlock(context.getSource(), name, info, EntityArgument.getPlayer(context, "target"), key, Optional.empty()))
                )
            )
            .then(literal("lock")
                .then(argument("id", ResourceArgument.resource(buildContext, key))
                    .executes(context -> lock(context.getSource(), name, info, EntityArgument.getPlayer(context, "target"), Optional.of(ResourceArgument.getResource(context, "id", key))))
                )
                .then(literal("*")
                    .executes(context -> lock(context.getSource(), name, info, EntityArgument.getPlayer(context, "target"), Optional.empty()))
                )
            )
        );
    }

    private static <T> int unlock(CommandSourceStack source, String name, UnlockedInfo info, ServerPlayer player, ResourceKey<Registry<T>> key, Optional<Holder.Reference<T>> entry) {
        if (entry.isEmpty()) {
            source.registryAccess().lookupOrThrow(key).listElements().forEach(holder -> info.add(player, holder.key().identifier()));
            source.sendSuccess(() -> Component.translatable("command.wikiful.unlocked_all." + name), false);
            return 1;
        }

        Identifier id = entry.get().key().identifier();
        if (info.hasUnlocked(player, id)) {
            source.sendFailure(Component.translatable("command.wikiful.already_unlocked." + name, id.toString()));
            return 0;
        }
        info.add(player, id);
        source.sendSuccess(() -> Component.translatable("command.wikiful.unlocked_single." + name, id.toString()), false);
        return 1;
    }

    private static <T> int lock(CommandSourceStack source, String name, UnlockedInfo info, ServerPlayer player, Optional<Holder.Reference<T>> entry) {
        if (entry.isEmpty()) {
            info.clear(player);
            source.sendSuccess(() -> Component.translatable("command.wikiful.locked_all." + name), false);
            return 1;
        }

        Identifier id = entry.get().key().identifier();
        if (!info.hasUnlocked(player, id)) {
            source.sendFailure(Component.translatable("command.wikiful.already_locked." + name, id.toString()));
            return 0;
        }
        info.remove(player, id);
        source.sendSuccess(() -> Component.translatable("command.wikiful.locked_single." + name, id.toString()), false);
        return 1;
    }
}
