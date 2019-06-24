package fr.badblock.bukkit.hub.v2.players;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
import fr.badblock.bukkit.hub.v2.npc.Hologram;
import fr.badblock.bukkit.hub.v2.players.addons.HubScoreboard;
import fr.badblock.bukkit.hub.v2.tasks.JoinTitleUpdateTask;
import fr.badblock.bukkit.hub.v2.utils.FNPC;
import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.databases.SQLRequestType;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.BadblockPlayer.BadblockMode;
import fr.badblock.gameapi.utils.BukkitUtils;
import fr.badblock.gameapi.utils.general.Callback;
import fr.badblock.gameapi.utils.threading.TempScheduler;
import lombok.Data;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;

@Data
public class HubPlayer
{

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

	private long gamePoints = -1;

	@Getter
	public Entity	 mountEntity;

	private List<Friend> friends = new ArrayList<>();

	public HubPlayer(BadblockPlayer player)
	{
		this.setPlayer(player);
		this.setName(player.getName());

		updateFriends();

		players.put(getName(), this);
	}

	public void updateFriends()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				List<Friend> tmp = new ArrayList<>();

				DB db = GameAPI.getAPI().getMongoService().getDb();
				DBCollection collection = db.getCollection("friendlist");

				DBObject query = new BasicDBObject();
				query.put("_owner", player.getUniqueId().toString());

				DBCursor cursor = collection.find(query);

				if (cursor.hasNext())
				{
					DBObject obj = cursor.next();
					@SuppressWarnings("unchecked")
					Map<String, BasicDBObject> players = (Map<String, BasicDBObject>) obj.get("players");
					for (Entry<String, BasicDBObject> player : players.entrySet())
					{
						DBCollection users = db.getCollection("players");
						DBObject q = new BasicDBObject();
						q.put("uniqueId", player.getKey());

						boolean accepted = "ACCEPTED".equals(player.getValue().getString("state"));

						DBCursor c = users.find(q);

						if (c.hasNext())
						{
							BasicDBObject co = (BasicDBObject) c.next();
							String name = co.getString("name");
							long keepalive = 0;
							if (co.containsField("keepalive"))
							{
								keepalive = co.getLong("keepalive");
							}
							String lastServer = co.getString("lastServer");
							Friend f = new Friend(name, accepted, keepalive > System.currentTimeMillis(), lastServer);
							tmp.add(f);
						}
					}
				}

				friends = tmp;
			}
		}.start();
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

					HubPlayer hubPlayer = HubPlayer.get(player);

					if (hubPlayer.getInventory() == null || hubPlayer.getInventory().isEmpty())
					{
						CustomItemActionType.OPEN_INV.work(player, CustomItemActionType.OPEN_INV, "selector_mode");
						player.sendTranslatedMessage("hub.items.selector_mode.pleaseselectamode");
					}
				}
			}, 1, 1);
		}

		// Toggle players
		Bukkit.getScheduler().runTask(BadBlockHub.getInstance(), new Runnable() {
			@Override
			public void run()
			{
				new JoinTitleUpdateTask(player);

				for (BadblockPlayer otherPlayer : BukkitUtils.getAllPlayers())
				{
					HubStoredPlayer stored = HubStoredPlayer.get(otherPlayer);

					if (stored != null && stored.isHidePlayers())
					{
						otherPlayer.hidePlayer(player);
					}
					else
					{
						otherPlayer.showPlayer(player);
					}
				}

				if (storedPlayer.isHidePlayers())
				{
					for (BadblockPlayer otherPlayer : BukkitUtils.getAllPlayers())
					{
						player.hidePlayer(otherPlayer);
					}

					getPlayer().sendTranslatedMessage("hub.toggleplayers.alwaysenabled");
					return;
				}

				for (BadblockPlayer otherPlayer : BukkitUtils.getAllPlayers())
				{
					if (!otherPlayer.getBadblockMode().equals(BadblockMode.PLAYER))
					{
						continue;
					}

					if (otherPlayer.getGameMode().equals(GameMode.SPECTATOR))
					{
						continue;
					}

					player.showPlayer(otherPlayer);
				}

				getPlayer().sendTranslatedMessage("hub.toggleplayers.youcandisableplayers");
			}
		});

		if (ConfigLoader.getGameHub().isEnabled())
		{
			String realName = player.getRealName() != null && !player.getRealName().isEmpty() ? player.getRealName() : player.getName();
			GameAPI.getAPI().getSqlDatabase().call("SELECT _points FROM " + ConfigLoader.getGameHub().getRankedGameName() + "_" + Hologram.getDate() +
					" WHERE playerName = '" + realName + "'", SQLRequestType.QUERY, new Callback<ResultSet>()
			{

				@Override
				public void done(ResultSet result, Throwable error)
				{
					try
					{
						if (result.next())
						{
							gamePoints = result.getLong("_points");
						}
					}
					catch (Exception err)
					{
						err.printStackTrace();
					}
				}
			});
		}

		return this;
	}

	public HubPlayer loadPlayer()
	{
		if (!isOnline())
		{
			return this;
		}

		getPlayer().setGameMode(GameMode.ADVENTURE);
		getPlayer().teleport(ConfigLoader.getLoc().getLocation("spawn"));
		//	getPlayer().addBossBar("hub", "", 1f, BossBarColor.RED, BossBarStyle.SOLID);
		InventoriesLoader.loadInventories(BadBlockHub.getInstance());
		giveDefaultInventory();
		setScoreboard(new HubScoreboard(getPlayer()));

		Bukkit.getScheduler().runTaskLater(BadBlockHub.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				for (FNPC npc : FNPC.npcs.values())
				{
					PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
					connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
							new EntityPlayer[] { npc.npc }));
					connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY,
							new EntityPlayer[] { npc.npc }));
					connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc.npc));
					connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc.npc, (byte) ((npc.getLocation().getYaw() * 256.0F) / 360.0F)));

					Bukkit.getScheduler().runTaskLater(BadBlockHub.getInstance(), new Runnable()
					{
						@Override
						public void run()
						{
							connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
									new EntityPlayer[] { npc.npc }));
						}
					}, 1);
				}
			}
		}, 40);

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
