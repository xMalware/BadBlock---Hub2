package fr.badblock.bukkit.hub.v2.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.EntityType;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.utils.EntityUtils;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.fakeentities.FakeEntity;
import fr.badblock.gameapi.packets.watchers.WatcherZombie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class NPC
{

	@Getter@Setter private static Map<String, NPC> npcs = new HashMap<>();

	private String					uniqueId;
	private String					displayName;
	private EntityType				entityType;
	private List<InventoryAction>	actions;
	private boolean					vip;
	private boolean					staff;
	private FakeLocation			location;
	private List<String>			permissions;

	private transient FakeEntity<?>	fakeEntity;

	public NPC(BasicDBObject dbObject)
	{
		this.uniqueId = dbObject.getString("uniqueId");
		this.displayName = dbObject.getString("displayName");
		this.entityType = EntityUtils.getEntityType(dbObject.getString("entityType"));
		this.vip = dbObject.getBoolean("vip");
		this.staff = dbObject.getBoolean("staff");
		this.location = new FakeLocation((BasicDBObject) dbObject.get("location"));
		
		BasicDBList dbList = (BasicDBList) dbObject.get("permissions");
		this.permissions = new ArrayList<>();
		
		for (Object string : dbList)
		{
			permissions.add(string.toString());
		}
		
		dbList = (BasicDBList) dbObject.get("actions");
		this.actions = new ArrayList<>();
		
		for (Object object : dbList)
		{
			BasicDBObject tempObject = (BasicDBObject) object;
			actions.add(new InventoryAction(tempObject));
		}
	}
	
	public NPC(String uniqueId, String displayName, EntityType entityType, List<InventoryAction> actions, boolean vip, boolean staff, FakeLocation location, List<String> permissions)
	{
		this.uniqueId = uniqueId;
		this.displayName = displayName;
		this.entityType = entityType;
		this.actions = actions;
		this.vip = vip;
		this.staff = staff;
		this.location = location;
		this.permissions = permissions;
	}
	
	public DBObject toObject()
	{
		BasicDBObject result = new BasicDBObject();
		result.append("uniqueId", uniqueId);
		result.append("displayName", displayName);
		result.append("entityType", entityType.name());
		BasicDBList dbList = new BasicDBList();
		for (InventoryAction action : actions)
		{
			dbList.add(action.toObject());
		}
		result.append("actions", dbList);
		result.append("vip", vip);
		result.append("staff", staff);
		result.append("location", location.toObject());
		result.append("permissions", permissions);
		return result;
	}

	public boolean isAlive()
	{
		return getFakeEntity() != null && !getFakeEntity().isRemoved();
	}
	
	public void spawn()
	{
		if (isAlive())
		{
			// Teleport
			if (!getFakeEntity().getLocation().equals(location.toLocation()))
			{
				getFakeEntity().teleport(location.toLocation());
			}
			// Change type
			if (!getFakeEntity().getType().equals(getEntityType()))
			{
				setFakeEntity(null);
				spawn();
			}
			return;
		}
		setFakeEntity(EntityUtils.spawn(getLocation().toLocation(), getEntityType(), WatcherZombie.class, false, false, false, false, getDisplayName()));
	}

	public void despawn()
	{
		if (!isAlive())
		{
			return;
		}
		getFakeEntity().remove();
		getFakeEntity().destroy();
	}
	
	public static NPC toNPC(DBObject object)
	{
		return GameAPI.getGson().fromJson(object.toString(), NPC.class);
	}

	
	public static String generateUniqueId()
	{
		return UUID.randomUUID().toString().split("-")[0];
	}

}