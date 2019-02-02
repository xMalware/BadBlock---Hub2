package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import fr.badblock.api.common.minecraft.InstanceKeepAlive;
import fr.badblock.api.common.tech.mongodb.MongoService;
import fr.badblock.api.common.utils.TimeUtils;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.instances.hosts.Host;
import fr.badblock.bukkit.hub.v2.rabbit.InstanceKeepAliveListener;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.GameAPI;

public class HostFetcherTask extends HubTask
{
	
	public static ConcurrentMap<String, Host> hosts = new ConcurrentHashMap<>();
	
	public HostFetcherTask()
	{
		super (false, 20, 20);
	}

	@Override
	public void run()
	{
		synchronize();
	}

	public static void synchronize()
	{
		MongoService mongoService = GameAPI.getAPI().getMongoService();
		DBCollection collection = mongoService.getDb().getCollection("host_servers");
		BasicDBObject dbQuery = new BasicDBObject();
		dbQuery.put("opened", true);
		DBCursor cursor = collection.find(dbQuery);

		Map<String, Host> list = new HashMap<>();
		
		while (cursor.hasNext())
		{
			BasicDBObject dbObject = (BasicDBObject) cursor.next();
			
			String serverName = dbObject.getString("server_name");
			if (!InstanceKeepAliveListener.games.containsKey(serverName))
			{
				continue;
			}
			
			InstanceKeepAlive keepAlive = InstanceKeepAliveListener.games.get(serverName);
			
			if (TimeUtils.isExpired(keepAlive.getKeepAliveTime()))
			{
				continue;
			}
			
			Host host = new Host(keepAlive, dbObject);
			list.put(keepAlive.getName(), host);
		}

		Bukkit.getScheduler().runTask(BadBlockHub.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				Iterator<Entry<String, Host>> iterator = hosts.entrySet().iterator();
				
				while (iterator.hasNext())
				{
					Entry<String, Host> entry = iterator.next();
					
					if (!list.containsKey(entry.getKey()))
					{
						iterator.remove();
					}
				}
				
				for (Entry<String, Host> entry : list.entrySet())
				{
					if (!hosts.containsKey(entry.getKey()))
					{
						hosts.put(entry.getKey(), entry.getValue());
					}
				}
			}
		});		
	}

}