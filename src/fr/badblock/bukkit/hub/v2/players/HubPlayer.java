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
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.players.addons.HubScoreboard;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.bossbars.BossBarColor;
import fr.badblock.gameapi.players.bossbars.BossBarStyle;
import lombok.Data;

@Data public class HubPlayer
{

	private static Map<String, HubPlayer>	players = new HashMap<>();

	private String							name;
	private BadblockPlayer					player;
	private HubStoredPlayer					storedPlayer;

	private HubScoreboard					scoreboard;
	
	private CustomDisguise					disguise;
	
	private String							inventory;
	private String							buyFeature;
	private CustomInventory			customInventory;
	private Map<Integer, InventoryAction[]> customActions;
	
	private boolean						jump;
	private boolean						jumpBeingTeleported;
	private int									jumpCheckpoint;
	
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
		if (!isOnline())
		{
			return this;
		}
		getPlayer().teleport(ConfigLoader.getLoc().getLocation("spawn"));
		getPlayer().addBossBar("hub", "", 1f, BossBarColor.RED, BossBarStyle.SOLID);
		InventoriesLoader.loadInventories(BadBlockHub.getInstance());
		giveDefaultInventory();
		setScoreboard(new HubScoreboard(getPlayer()));
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
