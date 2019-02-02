package fr.badblock.bukkit.hub.v2.instances.hosts;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import fr.badblock.api.common.minecraft.InstanceKeepAlive;
import fr.badblock.api.common.tech.mongodb.MongoService;
import fr.badblock.api.common.utils.TimeUtils;
import fr.badblock.gameapi.GameAPI;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Host
{

	private int								id;
	private InstanceKeepAlive		keepAlive;
	private String							owner;
	private String							type;
	private boolean						whitelistEnabled;
	private List<String>				whitelist;

	public Host(InstanceKeepAlive keepAlive, BasicDBObject dbObject)
	{
		assert keepAlive != null;
		assert dbObject != null;
		
		this.keepAlive = keepAlive;
		this.owner = dbObject.getString("owner");
		
		MongoService mongoService = GameAPI.getAPI().getMongoService();
		DB db = mongoService.getDb();
		DBCollection collection = db.getCollection("players");
		
		BasicDBObject query = new BasicDBObject();
		query.put("uniqueId", this.owner);
		
		DBCursor cursor = collection.find(query);
		if (cursor.hasNext())
		{
			BasicDBObject result = (BasicDBObject) cursor.next();
			this.owner = result.getString("name");
		}
		
		this.type = dbObject.getString("type");
		
		collection = db.getCollection("host_servertypes");
		
		query = new BasicDBObject();
		query.put("type", this.type);
		
		if (cursor.hasNext())
		{
			BasicDBObject result = (BasicDBObject) cursor.next();
			this.type = result.getString("displayName");
		}
		
		this.whitelistEnabled = dbObject.getBoolean("whitelistEnabled");
		this.whitelist = new ArrayList<>();

		Object objList = dbObject.get("whitelist");

		if (objList instanceof BasicDBList)
		{
			BasicDBList list = (BasicDBList) dbObject.get("whitelist");

			for (Object tmpObject : list)
			{
				this.whitelist.add(tmpObject.toString());
			}
		}
		
		String[] splitter = keepAlive.getName().split("_");
		
		if (splitter.length > 1)
		{
			this.id = Integer.parseInt(splitter[1]);
		}
	}
	
	public boolean isExpired()
	{
		assert getKeepAlive() != null;
		
		return TimeUtils.isValid(getKeepAlive().getKeepAliveTime());
	}

}
