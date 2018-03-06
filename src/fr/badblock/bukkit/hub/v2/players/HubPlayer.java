package fr.badblock.bukkit.hub.v2.players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguise;
import fr.badblock.bukkit.hub.v2.inventories.BukkitInventories;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.custom.CustomInventory;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;

@Data public class HubPlayer
{

	private static Map<String, HubPlayer>	players = new HashMap<>();

	private String							name;
	private BadblockPlayer					player;
	private HubStoredPlayer					storedPlayer;

	// Temp values
	private String							inventory;
	private String							buyFeature;
	private CustomDisguise					disguise;
	private CustomInventory					customInventory;

	public HubPlayer(BadblockPlayer player)
	{
		this.setPlayer(player);
		this.setName(player.getName());
		players.put(getName(), this);
	}

	public HubPlayer loadEverything()
	{
		loadData();
		loadPlayer();
		return this;
	}

	public HubPlayer loadData()
	{
		storedPlayer = HubStoredPlayer.get(getPlayer());
		return this;
	}

	public HubPlayer loadPlayer()
	{
		// TODO: load player : locations
		if (!isOnline())
		{
			return this;
		}
		getPlayer().teleport(ConfigLoader.getLoc().getLocation("spawn"));
		getPlayer().sendTranslatedBossBar("hubplayer.bossbar");
		InventoriesLoader.loadInventories(BadBlockHub.getInstance());
		giveDefaultInventory();
		new HubScoreboard(getPlayer());
		return this;
	}

	public HubPlayer giveDefaultInventory()
	{
		BukkitInventories.giveDefaultInventory(getPlayer());
		return this; 
	}

	public void unload()
	{
		players.remove(getName());
	}

	public boolean isOnline()
	{
		return getPlayer().isOnline();
	}

	public static HubPlayer initialize(BadblockPlayer player)
	{
		return new HubPlayer(player);
	}

	public static HubPlayer get(BadblockPlayer player)
	{
		return players.get(player.getName());
	}

	public static Collection<HubPlayer> getPlayers()
	{
		return players.values();
	}

}
