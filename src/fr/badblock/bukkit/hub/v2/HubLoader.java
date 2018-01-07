package fr.badblock.bukkit.hub.v2;

import java.io.File;

import fr.badblock.bukkit.hub.v2.commands.CommandsLoader;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.listeners.HubMapProtector;
import fr.badblock.bukkit.hub.v2.listeners.ListenerPackages;
import fr.badblock.bukkit.hub.v2.mounts.MountLoader;
import fr.badblock.bukkit.hub.v2.rabbit.RabbitLoader;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.GameAPI;

public class HubLoader {

	public static void load(BadblockPlugin plugin) {
		// Load configuration
		ConfigLoader.load(plugin);
		// Linked servers
		RabbitLoader.load();
		// Load listeners
		ListenerPackages.load(plugin);
		// Load commands
		CommandsLoader.load(plugin);
		// Load inventories
		InventoriesLoader.loadInventories(plugin);
		// Load API
		GameAPI api = plugin.getAPI();
		api.formatChat(true, false, "hub");
		api.getBadblockScoreboard().doGroupsPrefix();
		api.setMapProtector(new HubMapProtector());
		api.managePortals(new File(plugin.getDataFolder(), "portals"));
		// Load mounts
		MountLoader.load(plugin);
	}
	
}
	