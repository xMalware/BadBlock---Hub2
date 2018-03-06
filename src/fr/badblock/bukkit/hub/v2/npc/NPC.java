package fr.badblock.bukkit.hub.v2.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.EntityType;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.gameapi.GameAPI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class NPC
{
	
	@Getter@Setter private static List<NPC> npcs = new ArrayList<>();

	private UUID				uuid;
	private String				displayName;
	private EntityType			entityType;
	private InventoryAction[]	actions;
	private boolean				vip;
	private boolean				staff;
	private FakeLocation		location;
	
	public DBObject toObject()
	{
		BasicDBObject result = new BasicDBObject();
		result.append("uniqueId", uuid.toString());
		result.append("displayName", displayName);
		result.append("entityType", entityType.name());
		result.append("actions", actions);
		result.append("vip", vip);
		result.append("staff", staff);
		result.append("location", location.toObject());
		return result;
	}

	public static NPC toNPC(DBObject object)
	{
		return GameAPI.getGson().fromJson(object.toString(), NPC.class);
	}
	
}
