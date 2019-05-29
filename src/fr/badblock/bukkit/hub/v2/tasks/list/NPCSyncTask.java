package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import fr.badblock.api.common.tech.mongodb.MongoService;
import fr.badblock.api.common.utils.FullSEntry;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.npc.NPC;
import fr.badblock.bukkit.hub.v2.rabbit.SEntryInfosListener;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.i18n.Locale;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class NPCSyncTask extends HubTask
{

	public NPCSyncTask()
	{
		super (false, 20, 20 * 5);
	}

	@Override
	public void run()
	{
		synchronize();
	}

	public static void synchronize()
	{
		if (!ConfigLoader.getSwitchers().isGameEnabled())
		{
			return;
		}

		MongoService mongoService = GameAPI.getAPI().getMongoService();
		DBCollection collection = mongoService.getDb().getCollection("npc");
		BasicDBObject dbQuery = new BasicDBObject();
		DBCursor cursor = collection.find(dbQuery);

		Map<String, NPC> list = new HashMap<>();
		while (cursor.hasNext())
		{
			NPC npc = NPC.toNPC(cursor.next());
			list.put(npc.getUniqueId(), npc);
		}

		Bukkit.getScheduler().runTask(BadBlockHub.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				for (NPC npc : NPC.getNpcs().values())
				{
					if (!list.containsKey(npc.getUniqueId()))
					{
						// Removed
						npc.despawn();
						NPC.getNpcs().remove(npc.getUniqueId());
					}
				}

				for (NPC npc : list.values())
				{
					if (!NPC.getNpcs().containsKey(npc.getUniqueId()))
					{
						// Added
						npc.spawn();
						NPC.getNpcs().put(npc.getUniqueId(), npc);
					}
				}
				
				for (NPC npc : NPC.getNpcs().values())
				{
					if (npc.getActions() != null && !npc.getActions().isEmpty())
					{
						InventoryAction action = npc.getActions().iterator().next();
						if ((action.getAction().equals(CustomItemActionType.TELEPORT_SERVER) || action.getAction().equals(CustomItemActionType.WAITING_LINE)) && npc.getSentryQueue() != null && npc.getPlayerText() != null)
						{
							int count = -1;
							if (SEntryInfosListener.sentries.containsKey(npc.getSentryQueue()))
							{
								FullSEntry sentry = SEntryInfosListener.sentries.get(npc.getSentryQueue());
								count = sentry.getIngamePLayers() + sentry.getWaitinglinePlayers();
							}

							npc.getPlayerText().setCustomName(new TranslatableString("hub.gamepnj.ingameplayers", count).getAsLine(Locale.FRENCH_FRANCE));
						}
					}
				}
			}
		});		
	}

}