package fr.badblock.bukkit.hub.v2.commands.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.npc.FakeLocation;
import fr.badblock.bukkit.hub.v2.npc.NPC;
import fr.badblock.bukkit.hub.v2.tasks.NPCSyncTask;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer.GamePermission;
import fr.badblock.gameapi.utils.general.StringUtils;
import fr.badblock.gameapi.utils.i18n.TranslatableString;
import fr.toenga.common.tech.mongodb.MongoService;
import net.md_5.bungee.api.ChatColor;

public class NPCCommand extends AbstractCommand
{

	public NPCCommand()
	{
		super("npc", new TranslatableString("commands.npc.usage"), GamePermission.ADMIN, GamePermission.ADMIN, GamePermission.ADMIN);
		this.allowConsole(true);
	}

	/**
	 *  /npc help
	 *	/npc add <entityType> <vip> <staff> <displayName>
	 *	/npc list
	 *	/npc <id>
	 *				remove
	 *				action <clickType> <actionType> <actionData>
	 *				displayName <displayName>
	 *				setloc
	 *				vip <trueorfalse>
	 *				staff <trueorfalse>
	 *				type <entityType>
	 *				permissions
	 *								list
	 *								add <permission>
	 *								remove <permission>
	 */

	@Override
	public boolean executeCommand(CommandSender sender, String[] args)
	{
		// No subcommand
		if (args.length == 0)
		{
			help(sender);
			return true;
		}
		// Subcommands
		switch (args[0])
		{
		case "add":
			add(sender, args);
			break;
		case "list":
			list(sender);
			break;
		case "help":
			help(sender);
			break;
		default:
			manageNPC(sender, args);
			break;
		}
		return true;
	}

	/**
	 * /npc add <entityType> <vip> <staff> <displayName>
	 * @param sender
	 * @param args
	 */
	private void add(CommandSender sender, String[] args)
	{
		// Not enough arguments
		if (args.length < 5)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.add.usage");
			return;
		}

		// Not a player
		if (!(sender instanceof Player))
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.add.notaplayer");
		}

		Player player = (Player) sender;

		// Entity type
		String rawEntityType = args[1];
		EntityType entityType = getEntityType(rawEntityType);

		if (entityType == null)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.add.unknownentitytype");
			return;
		}

		// VIP
		String rawVip = args[2];
		boolean vip = false;

		try
		{
			vip = Boolean.parseBoolean(rawVip);
		}
		catch (Exception error)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.add.mustbeboolean");
			return;
		}

		// VIP
		String rawStaff = args[3];
		boolean staff = false;

		try
		{
			staff = Boolean.parseBoolean(rawStaff);
		}
		catch (Exception error)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.add.mustbeboolean");
			return;
		}

		String displayName = StringUtils.join(args, " ", 4);
		displayName = ChatColor.translateAlternateColorCodes('&', displayName);

		String uniqueId = NPC.generateUniqueId();

		// Make NPCs great again!
		NPC npc = new NPC(uniqueId, displayName, entityType, null, vip, staff, new FakeLocation(player.getLocation()), new ArrayList<>());

		// Insert NPC
		MongoService mongoService = GameAPI.getAPI().getMongoService();
		DBCollection collection = mongoService.getDb().getCollection("npc");
		collection.insert(npc.toObject());

		// Update NPC
		NPCSyncTask.synchronize();

		// Done
		GameAPI.i18n().sendMessage(sender, "hub.npc.add.added", uniqueId);
	}

	private void list(CommandSender sender)
	{
		GameAPI.i18n().sendMessage(sender, "hub.npc.list.header", NPC.getNpcs().size());		
		for (NPC npc : NPC.getNpcs().values())
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.list.id", npc.getUuid(), npc.getDisplayName());		
		}
		GameAPI.i18n().sendMessage(sender, "hub.npc.list.footer", NPC.getNpcs().size());
	}

	/*
	 * Manage NPCs.
	 * 
	 * 	 	/npc <id>
	 *				remove
	 *				action <clickType> <actionType> <actionData>
	 *				displayName <displayName>
	 *				setloc
	 *				vip <trueorfalse>
	 *				staff <trueorfalse>
	 *				type <entityType>
	 *				permissions
	 *								list
	 *								add <permission>
	 *								remove <permission>
	 */
	private void manageNPC(CommandSender sender, String[] args)
	{
		if (args.length < 2)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.usage", NPC.getNpcs().size());
			return;
		}

		String rawUniqueId = args[0];

		// Unknown NPC
		if (!(NPC.getNpcs().containsKey(rawUniqueId)))
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.unknownnpc", rawUniqueId);
			return;
		}

		String action = args[1];

		NPC npc = NPC.getNpcs().get(rawUniqueId);

		switch (action)
		{
		case "remove":
			remove(sender, npc);
			break;
		case "action":
			action(sender, npc, args);
			break;
		case "displayname":
			displayName(sender, npc, args);
			break;
		case "setloc":
			setLoc(sender, npc);
			break;
		case "vip":
			vip(sender, npc, args);
			break;
		case "staff":
			staff(sender, npc, args);
			break;
		case "type":
			type(sender, npc, args);
			break;
		case "permissions":
			permissions(sender, npc, args);
			break;
		default:
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.unknownaction", rawUniqueId);
			break;
		}
	}

	/**
	 * Update NPC
	 * Warning: Don't change uniqueId!
	 * @param npc
	 */
	private void update(NPC npc)
	{
		MongoService mongoService = GameAPI.getAPI().getMongoService();
		DBCollection collection = mongoService.getDb().getCollection("npc");

		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("uniqueId", npc.getUuid());

		collection.update(dbObject, npc.toObject());
	}

	/*
	 * Remove NPC
	 * 
	 * 	 	/npc <id>
	 *				remove
	 */
	private void remove(CommandSender sender, NPC npc)
	{
		MongoService mongoService = GameAPI.getAPI().getMongoService();
		DBCollection collection = mongoService.getDb().getCollection("npc");

		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("uniqueId", npc.getUuid());

		collection.remove(dbObject);	
		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.removed", npc.getUuid());
	}

	/*
	 * Manage NPC actions
	 * 
	 * 	 	/npc <id>
	 *				action <clickType> <actionType> <actionData>
	 */
	private void action(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length != 5)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.action.usage", npc.getUuid());
			return;
		}

		String rawClickType = args[2];
		InventoryActionType clickType = InventoryActionType.fromString(rawClickType);

		// Unknown click type
		if (clickType == null)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.action.unknownclicktype", rawClickType);
			return;
		}

		String rawActionType = args[3];
		CustomItemActionType customItemActionType = CustomItemActionType.fromString(rawActionType);

		// Unknown action type
		if (customItemActionType == null)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.action.unknownactiontype", rawActionType);
			return;
		}

		// Action data
		String actionData = args[4];

		InventoryAction inventoryAction = new InventoryAction(clickType, customItemActionType, actionData);

		if (npc.getActions() == null)
		{
			npc.setActions(new InventoryAction[] { inventoryAction });
		}
		else
		{
			Map<InventoryActionType, InventoryAction> actionMap = new HashMap<>();

			Arrays.stream(npc.getActions()).forEach(action -> actionMap.put(action.getActionType(), action));
			actionMap.put(clickType, inventoryAction);

			InventoryAction[] actions = actionMap.values().stream().toArray(InventoryAction[]::new);
			npc.setActions(actions);
		}

		// Update NPC
		update(npc);

		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.action.updatedaction", npc.getUuid(), clickType.name(), customItemActionType.name(), actionData);
	}

	/*
	 * Manage NPC actions
	 * 
	 * 	 	/npc <id>
	 *				displayname <displayName>
	 */
	private void displayName(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length < 3)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.displayname.usage", npc.getUuid());
			return;
		}

		String displayName = StringUtils.join(args, " ", 2);
		displayName = ChatColor.translateAlternateColorCodes('&', displayName);

		// Update NPC
		update(npc);

		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.displayname.updated", npc.getUuid(), displayName);
	}

	/*
	 * Set NPC location
	 * 
	 * 	 	/npc <id>
	 *				setloc
	 */
	private void setLoc(CommandSender sender, NPC npc)
	{
		// Not a player
		if (!(sender instanceof Player))
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.setloc.notaplayer");
			return;
		}

		Player player = (Player) sender;

		npc.setLocation(new FakeLocation(player.getLocation()));

		// Update NPC
		update(npc);

		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.setloc.updated", npc.getUuid(), npc.getLocation());
	}

	/*
	 * Set vip access for NPC
	 * 
	 * 	 	/npc <id>
	 *				vip <trueorfalse>
	 */
	private void vip(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length < 3)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.vip.usage", npc.getUuid());
			return;
		}

		String rawVip = args[2];

		boolean vip = false;

		try
		{
			vip = Boolean.parseBoolean(rawVip);
		}
		catch (Exception error)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.vip.mustbeboolean");
			return;
		}

		npc.setVip(vip);

		// Update NPC
		update(npc);

		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.vip.updated", npc.getUuid(), npc.isVip());
	}

	/*
	 * Set staff access for NPC
	 * 
	 * 	 	/npc <id>
	 *				vip <trueorfalse>
	 */
	private void staff(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length < 3)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.staff.usage", npc.getUuid());
			return;
		}

		String rawStaff = args[2];

		boolean staff = false;

		try
		{
			staff = Boolean.parseBoolean(rawStaff);
		}
		catch (Exception error)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.staff.mustbeboolean");
			return;
		}

		npc.setStaff(staff);

		// Update NPC
		update(npc);

		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.staff.updated", npc.getUuid(), npc.isStaff());
	}

	/*
	 * Set the entity type of a NPC
	 * 
	 * 	 	/npc <id>
	 *				type <entitytype>
	 */
	private void type(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length < 3)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.type.usage", npc.getUuid());
			return;
		}

		String rawType = args[2];
		EntityType entityType = getEntityType(rawType);

		if (entityType == null)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.type.unknownentitytype");
			return;
		}

		npc.setEntityType(entityType);

		// Update NPC
		update(npc);

		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.type.updated", npc.getUuid(), npc.getEntityType().name());
	}

	/**
	 * 
	 * Manage NPC permissions	
	 * 
	 * 		/npc <id>
	 * 				permissions
	 *							list
	 *							add <permission>
	 *							remove <permission>
	 */
	private void permissions(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length < 3)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.usage", npc.getUuid());
			return;
		}

		String action = args[0];

		switch (action)
		{
		case "list":
			listPermissions(sender, npc);
			break;
		case "add":
			addPermission(sender, npc, args);
			break;
		case "remove":
		case "delete":
			removePermission(sender, npc, args);
			break;
		default:
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.usage", npc.getUuid());
			break;
		}	
	}

	/**
	 * 
	 * List NPC permissions	
	 * 
	 * 		/npc <id>
	 * 				permissions
	 *							list
	 */
	private void listPermissions(CommandSender sender, NPC npc)
	{
		// Avoid NPEs
		if (npc.getPermissions() == null)
		{
			npc.setPermissions(new ArrayList<>());
		}

		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.list.header", npc.getUuid(), npc.getPermissions().size());
		for (String permission : npc.getPermissions())
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.list.permission", permission);	
		}
		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.list.footer", npc.getUuid(), npc.getPermissions().size());
	}


	/**
	 * 
	 * Add a permission
	 * 
	 * 		/npc <id>
	 * 				permissions
	 *							add <permission>
	 */
	private void addPermission(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length != 4)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.add.usage", npc.getUuid());
			return;
		}

		String permission = args[3].toLowerCase();
		
		// Avoid NPEs
		avoidNPEPermissions(npc);

		// Permission already in list
		if (npc.getPermissions().contains(permission))
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.add.already", npc.getUuid(), permission);
			return;
		}
		
		// Add permission
		npc.getPermissions().add(permission);
		
		// Update
		update(npc);
		
		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.add.added", npc.getUuid(), permission);
	}
	
	/**
	 * 
	 * Remove a permission
	 * 
	 * 		/npc <id>
	 * 				permissions
	 *							remove <permission>
	 */
	private void removePermission(CommandSender sender, NPC npc, String[] args)
	{
		if (args.length != 4)
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.remove.usage", npc.getUuid());
			return;
		}

		String permission = args[3].toLowerCase();
		
		// Avoid NPEs
		avoidNPEPermissions(npc);

		// Unknown permission
		if (!npc.getPermissions().contains(permission))
		{
			GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.remove.unknown", npc.getUuid(), permission);
			return;
		}
		
		// Delete permission
		npc.getPermissions().remove(permission);
		
		// Update
		update(npc);
		
		GameAPI.i18n().sendMessage(sender, "hub.npc.manage.permissions.remove.removed", npc.getUuid(), permission);
	}

	private void avoidNPEPermissions(NPC npc)
	{
		if (npc.getPermissions() == null)
		{
			npc.setPermissions(new ArrayList<>());
		}
	}

	private void help(CommandSender sender)
	{
		// TODO
	}

	private EntityType getEntityType(String rawName)
	{
		for (EntityType entityType : EntityType.values())
		{
			if (rawName.equalsIgnoreCase(entityType.name()))
			{
				return entityType;

			}
		}
		return null;
	}

}