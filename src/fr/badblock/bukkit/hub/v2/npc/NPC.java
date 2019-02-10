package fr.badblock.bukkit.hub.v2.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import fr.badblock.api.common.utils.FullSEntry;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.rabbit.SEntryInfosListener;
import fr.badblock.bukkit.hub.v2.utils.EntityUtils;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.fakeentities.FakeEntity;
import fr.badblock.gameapi.packets.watchers.WatcherZombie;
import fr.badblock.gameapi.utils.i18n.Locale;
import fr.badblock.gameapi.utils.i18n.TranslatableString;
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
	private List<ArmorStand> armorStands;
	private String						sentryQueue;
	private ArmorStand			playerText;

	private transient FakeEntity<?>	fakeEntity;

	public NPC(BasicDBObject dbObject)
	{
		this.uniqueId = dbObject.getString("uniqueId");

		if (dbObject.containsField("sentryQueue"))
		{
			this.sentryQueue = dbObject.getString("sentryQueue");
		}

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

		this.armorStands = new ArrayList<>();
	}

	public NPC(String uniqueId, String displayName, String sentryQueue, EntityType entityType, List<InventoryAction> actions, boolean vip, boolean staff, FakeLocation location, List<String> permissions)
	{
		this.uniqueId = uniqueId;
		this.displayName = displayName;
		this.sentryQueue = sentryQueue;
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
		result.append("sentryQueue", sentryQueue);
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

		setFakeEntity(EntityUtils.spawn(getLocation().toLocation(), getEntityType(), WatcherZombie.class, false, false, false, false, ""));

		if (getArmorStands() == null)
		{
			setArmorStands(new ArrayList<>());
		}

		Location loc = location.toLocation().clone();
		loc.add(0, 0.5, 0);
		spawnNametag(loc, displayName);
		loc = location.toLocation().clone();
		loc.add(0, 0.25, 0);
		spawnNametag(loc, new TranslatableString("hub.gamepnj.clicktoplay").getAsLine(Locale.FRENCH_FRANCE));

		if (actions != null && !actions.isEmpty())
		{
			InventoryAction action = actions.iterator().next();

			if (action.getAction().equals(CustomItemActionType.TELEPORT_SERVER) && sentryQueue != null)
			{
				loc = location.toLocation().clone();

				int count = -1;

				if (SEntryInfosListener.sentries.containsKey(sentryQueue))
				{
					FullSEntry sentry = SEntryInfosListener.sentries.get(sentryQueue);
					count = sentry.getIngamePLayers() + sentry.getWaitinglinePlayers();
				}

				playerText = spawnNametag(loc, new TranslatableString("hub.gamepnj.ingameplayers", count).getAsLine(Locale.FRENCH_FRANCE));
			}
		}
	}

	public void despawn()
	{
		if (!isAlive())
		{
			return;
		}

		getFakeEntity().remove();
		getFakeEntity().destroy();

		if (getArmorStands() != null)
		{
			getArmorStands().forEach(armorStand -> armorStand.remove());
		}

		if (playerText != null)
		{
			playerText.remove();
		}
	}

	public static NPC toNPC(DBObject object)
	{
		return GameAPI.getGson().fromJson(object.toString(), NPC.class);
	}

	public static String generateUniqueId()
	{
		return UUID.randomUUID().toString().split("-")[0];
	}

	public static ArmorStand spawnNametag(Location location, String text) {
		ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND); //Spawn the ArmorStand

		as.setGravity(false); //Make sure it doesn't fall
		as.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
		as.setCustomName(text); //Set this to the text you want
		as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
		as.setVisible(false); //Makes the ArmorStand invisible

		return as;
	}

}