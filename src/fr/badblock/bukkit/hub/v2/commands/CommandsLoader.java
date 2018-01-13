package fr.badblock.bukkit.hub.v2.commands;

import java.io.IOException;

import org.bukkit.Bukkit;

import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.utils.BukkitUtils;

public class CommandsLoader {
//
	private static String[] string = new String[] {
			"fr.badblock.badblock.hub.v2.commands.list"
	};

	public static void load(BadblockPlugin plugin) {
		loadCommands(plugin);
	}

	// Load commands
	private static void loadCommands(BadblockPlugin plugin) {
		try {
			BukkitUtils.instanciateListenersAndCommandsFrom(plugin, string);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.shutdown();
		}
	}
}
