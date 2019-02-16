package fr.badblock.bukkit.hub.v2.players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguise;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.AbstractGadgets;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.CustomPet;
import fr.badblock.bukkit.hub.v2.inventories.BukkitInventories;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.custom.CustomInventory;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.players.addons.HubScoreboard;
import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.BadblockPlayer.BadblockMode;
import fr.badblock.gameapi.players.bossbars.BossBarColor;
import fr.badblock.gameapi.players.bossbars.BossBarStyle;
import fr.badblock.gameapi.utils.BukkitUtils;
import fr.badblock.gameapi.utils.threading.TempScheduler;
import lombok.Data;
import lombok.Getter;

@Data
public class HubPlayer {

	private static Map<String, HubPlayer> players = new HashMap<>();

	private String name;
	private BadblockPlayer player;
	private HubStoredPlayer storedPlayer;

	private HubScoreboard scoreboard;

	private CustomDisguise disguise;

	private String inventory;
	private String buyFeature;
	private CustomInventory customInventory;
	private Map<Integer, InventoryAction[]> customActions;

	private boolean jump;
	private boolean jumpBeingTeleported;
	private int jumpCheckpoint;

	private AbstractGadgets currentWidget;
	private CustomPet pet;
	private Effect effect;

	@Getter
	public Entity	 mountEntity;

	public HubPlayer(BadblockPlayer player) {
		this.setPlayer(player);
		this.setName(player.getName());
		players.put(getName(), this);
	}

	public HubPlayer loadEverything() {
		loadData();
		loadPlayer();
		return this;
	}

	public HubPlayer loadData()
	{
		storedPlayer = HubStoredPlayer.get(getPlayer());

		if (!storedPlayer.isModeSelected())
		{
			TempScheduler tmp = new TempScheduler();
			tmp.task = Bukkit.getScheduler().runTaskTimer(BadBlockHub.getInstance(), new Runnable()
			{
				@Override
				public void run()
				{
					if (!player.isOnline())
					{
						tmp.task.cancel();
						return;
					}
					
					if (storedPlayer.isModeSelected())
					{
						tmp.task.cancel();
						return;
					}
					
					if (player.getOpenInventory() == null || player.getOpenInventory().getTopInventory() == null)
					{
						HubPlayer hubPlayer = HubPlayer.get(player);
						
						if (hubPlayer.getInventory() == null || hubPlayer.getInventory().isEmpty())
						{
							CustomItemActionType.OPEN_INV.work(player, CustomItemActionType.OPEN_INV, "selector_mode");
						}
					}
				}
			}, 1, 1);
		}

		// Toggle players
		Bukkit.getScheduler().runTask(BadBlockHub.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (storedPlayer.isHidePlayers()) {
					for (BadblockPlayer otherPlayer : BukkitUtils.getAllPlayers()) {
						player.hidePlayer(otherPlayer);
					}
					getPlayer().sendTranslatedMessage("hub.toggleplayers.alwaysenabled");
					return;
				}

				for (BadblockPlayer otherPlayer : BukkitUtils.getAllPlayers()) {
					if (!otherPlayer.getBadblockMode().equals(BadblockMode.PLAYER)) {
						continue;
					}

					if (otherPlayer.getGameMode().equals(GameMode.SPECTATOR)) {
						continue;
					}

					player.showPlayer(otherPlayer);
				}

				getPlayer().sendTranslatedMessage("hub.toggleplayers.youcandisableplayers");
			}
		});
		
		return this;
	}

	public HubPlayer loadPlayer() {
		if (!isOnline()) {
			return this;
		}

		if (getPlayer().hasPermission("hub.fly")) {
			getPlayer().setAllowFlight(true);
			getPlayer().setFlying(true);
		}

		getPlayer().setGameMode(GameMode.ADVENTURE);
		getPlayer().teleport(ConfigLoader.getLoc().getLocation("spawn"));
		getPlayer().addBossBar("hub", "", 1f, BossBarColor.RED, BossBarStyle.SOLID);
		InventoriesLoader.loadInventories(BadBlockHub.getInstance());
		giveDefaultInventory();
		setScoreboard(new HubScoreboard(getPlayer()));
		return this;
	}

	public HubPlayer giveDefaultInventory() {
		BukkitInventories.giveDefaultInventory(getPlayer());
		return this;
	}

	public void unload() {
		players.remove(getName());
	}

	public boolean isOnline() {
		return getPlayer().isOnline();
	}

	public static HubPlayer initialize(BadblockPlayer player) {
		return new HubPlayer(player);
	}

	public static HubPlayer get(BadblockPlayer player) {
		return players.get(player.getName());
	}

	public static HubPlayer get(String playerName) {
		return players.get(playerName);
	}

	public static Collection<HubPlayer> getPlayers() {
		return players.values();
	}

}
