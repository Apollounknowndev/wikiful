package dev.worldgen.wikiful.impl.command;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.function.Predicate;

public class WikifulCommandActual {
	@Actual
	private static Predicate<CommandSourceStack> hasGameMasterPermissions() {
		return Commands.hasPermission(Commands.LEVEL_GAMEMASTERS);
	}
}
