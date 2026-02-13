package dev.worldgen.wikiful.impl;

import net.minecraft.commands.CommandSourceStack;

import java.util.function.Predicate;

public class ClocheHack {
	public static Predicate<CommandSourceStack> hasGameMasterPermissions() {
		return stack -> stack.hasPermission(2);
	}
}
