package fr.badblock.bukkit.hub.v2;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.block.Block;

import fr.badblock.bukkit.hub.v2.commands.CommandsLoader;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountLoader;
import fr.badblock.bukkit.hub.v2.games.GamesManager;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.listeners.HubMapProtector;
import fr.badblock.bukkit.hub.v2.listeners.ListenerPackages;
import fr.badblock.bukkit.hub.v2.tasks.HubTaskLoader;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.selections.CuboidSelection;

public class HubLoader {

	public static void load(BadblockPlugin plugin)
	{
		// Load configuration
		ConfigLoader.load(plugin);
		// Load listeners
		ListenerPackages.load(plugin);
		// Load commands
		CommandsLoader.load(plugin);
		// Load inventories
		InventoriesLoader.loadInventories(plugin);
		// Load Mini-Games
		GamesManager.load(plugin);
		// Load API
		GameAPI api = plugin.getAPI();
		api.formatChat(true, false, "hub");
		api.getBadblockScoreboard().doGroupsPrefix();
		api.setMapProtector(new HubMapProtector());
		api.managePortals(new File(plugin.getDataFolder(), "portals"));
		// Load mounts
		MountLoader.load(plugin);
		// Load tasks
		HubTaskLoader.load(plugin);
		// Preload chunks
		preloadChunks();
	}

	private static void preloadChunks()
	{
		Location spawnLocation = ConfigLoader.getLoc().getLocation("spawn");

		Location firstLocation = spawnLocation.clone();
		firstLocation.setX(firstLocation.getX() - 32);
		firstLocation.setZ(firstLocation.getZ() + 32);
		
		Location secondLocation = spawnLocation.clone();
		secondLocation.setX(firstLocation.getX() + 32);
		secondLocation.setZ(firstLocation.getZ() - 32);
		
		CuboidSelection cuboidSelection = new CuboidSelection(firstLocation, secondLocation);
		
		for (Block block : cuboidSelection.getBlocks())
		{
			block.getChunk().load(false);
		}
	}

}
