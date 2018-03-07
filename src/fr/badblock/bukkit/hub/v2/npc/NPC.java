package fr.badblock.bukkit.hub.v2.npc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.EntityType;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.gameapi.GameAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class NPC
{
	
	@Getter@Setter private static Map<String, NPC> npcs = new HashMap<>();

	private String				uuid;
	private String				displayName;
	private EntityType			entityType;
	private InventoryAction[]	actions;
	private boolean				vip;
	private boolean				staff;
	private FakeLocation		location;
	private List<String>		permissions;
	
	public DBObject toObject()
	{
		BasicDBObject result = new BasicDBObject();
		result.append("uniqueId", uuid);
		result.append("displayName", displayName);
		result.append("entityType", entityType.name());
		result.append("actions", actions);
		result.append("vip", vip);
		result.append("staff", staff);
		result.append("location", location.toObject());
		result.append("permissions", permissions);
		return result;
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
