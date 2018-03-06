package fr.badblock.bukkit.hub.v2.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

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
		MongoService mongoService = GameAPI.getAPI().getMongoService();
		DBCollection collection = mongoService.getDb().getCollection("npc");
		BasicDBObject dbQuery = new BasicDBObject();
		collection.insert(new NPC(UUID.randomUUID(), "test", EntityType.BAT, null, false, false, Bukkit.getWorlds().get(0).getSpawnLocation()).toObject());
		DBCursor cursor = collection.find(dbQuery);
		
		List<NPC> list = new ArrayList<>();
		while (cursor.hasNext())
		{
			NPC npc = NPC.toNPC(cursor.next());
			list.add(npc);
		}
		NPC.setNpcs(list);
	}

}
