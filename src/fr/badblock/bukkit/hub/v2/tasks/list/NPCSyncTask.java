package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import fr.badblock.api.common.tech.mongodb.MongoService;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.npc.NPC;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.GameAPI;

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
			}
		});		
	}

}