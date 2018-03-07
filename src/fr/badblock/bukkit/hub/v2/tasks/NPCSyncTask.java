package fr.badblock.bukkit.hub.v2.tasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.npc.NPC;
import fr.badblock.gameapi.GameAPI;
import fr.toenga.common.tech.mongodb.MongoService;

public class NPCSyncTask implements Runnable
{
	
	public NPCSyncTask()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(BadBlockHub.getInstance(), this, 20, 20 * 5);
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
			list.put(npc.getUuid(), npc);
		}
		
		for (NPC npc : NPC.getNpcs().values())
		{
			if (!list.containsKey(npc.getUuid()))
			{
				// Removed
			}
		}
		
		for (NPC npc : list.values())
		{
			if (!NPC.getNpcs().containsKey(npc.getUuid()))
			{
				// Added
			}
		}
		
		NPC.setNpcs(list);
	}

}
