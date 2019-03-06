package fr.badblock.bukkit.hub.v2;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;

import com.google.gson.reflect.TypeToken;

import fr.badblock.api.common.utils.FullSEntry;
import fr.badblock.api.common.utils.general.FileUtils;
import fr.badblock.bukkit.hub.v2.commands.CommandsLoader;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.games.GamesManager;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.listeners.HubMapProtector;
import fr.badblock.bukkit.hub.v2.listeners.ListenerPackages;
import fr.badblock.bukkit.hub.v2.listeners.packets.UseEntityPacketListener;
import fr.badblock.bukkit.hub.v2.npc.NPC;
import fr.badblock.bukkit.hub.v2.rabbit.SEntryInfosListener;
import fr.badblock.bukkit.hub.v2.tasks.HubTaskLoader;
import fr.badblock.bukkit.hub.v2.utils.DisguiseUtil;
import fr.badblock.bukkit.hub.v2.utils.ParticleListener;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.i18n.Locale;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class HubLoader {

	public static final Type npcType = new TypeToken<List<NPC>>() {
	}.getType();
	
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

		if (ConfigLoader.getSwitchers().isGameEnabled())
		{
			// Load Mini-Games
			GamesManager.load(plugin);
		}

		// Load API
		GameAPI api = plugin.getAPI();
		api.formatChat(true, false, "hub");
		api.getBadblockScoreboard().doGroupsPrefix();
		api.setMapProtector(new HubMapProtector());
		api.managePortals(new File(plugin.getDataFolder(), "portals"));
		// Load tasks
		HubTaskLoader.load(plugin);
		// Clean hub world
		clean();

		// Load listener Particle
		ParticleListener.registerParticleListener();

		DisguiseUtil.register();

		if (ConfigLoader.getSwitchers().isGameEnabled())
		{
			//tmp
			Location l = new Location(Bukkit.getWorld("world"), -35, 108, -112);
			Sign sign = (Sign) l.getBlock().getState();
			sign.setLine(0, "§c[Gladiators]");
			sign.setLine(1, "§3Forest");
			sign.setLine(2, "§7§lJoueurs:");
			sign.setLine(3, "0");
			sign.update();

			l = new Location(Bukkit.getWorld("world"), -33, 108, -112);
			sign = (Sign) l.getBlock().getState();
			sign.setLine(0, "§c[Gladiators]");
			sign.setLine(1, "§3Ice");
			sign.setLine(2, "§7§lJoueurs:");
			sign.setLine(3, "0");
			sign.update();
		}

		if (ConfigLoader.getGameHub().isEnabled())
		{
			try
			{
				new UseEntityPacketListener();
				String data = FileUtils.readFile(new File(plugin.getDataFolder(), "npc.json"));
				List<NPC> npc = GameAPI.getGson().fromJson(data, npcType);
				for (NPC n : npc)
				{
					NPC.getNpcs().put(n.getUniqueId(), n);
					n.spawn();
					
					if (n.getActions() != null && !n.getActions().isEmpty())
					{
						InventoryAction action = n.getActions().iterator().next();

						if (action.getAction().equals(CustomItemActionType.TELEPORT_SERVER) && n.getSentryQueue() != null && n.getPlayerText() != null)
						{
							int count = -1;
							
							if (SEntryInfosListener.sentries.containsKey(n.getSentryQueue()))
							{
								FullSEntry sentry = SEntryInfosListener.sentries.get(n.getSentryQueue());
								count = sentry.getIngamePLayers() + sentry.getWaitinglinePlayers();
							}
							
							n.getPlayerText().setCustomName(new TranslatableString("hub.gamepnj.ingameplayers", count).getAsLine(Locale.FRENCH_FRANCE));
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}

	private static void clean()
	{
		for (World world : Bukkit.getWorlds())
		{
			world.setTime(1200);
		}
	}

}
