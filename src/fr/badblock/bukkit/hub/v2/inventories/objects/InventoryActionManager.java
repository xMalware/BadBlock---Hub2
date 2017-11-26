package fr.badblock.bukkit.hub.v2.inventories.objects;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.inventories.BukkitInventories;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.sentry.SEntry;

public class InventoryActionManager {

	public static void handle(BadblockPlayer player, String inventoryName, InventoryItemObject object, InventoryActionType type) {
		// No defined type
		if (type == null) return;
		for (InventoryAction inventoryAction : object.getActions()) {
			if (!inventoryAction.getActionType().equals(type)) continue;
			CustomItemAction action = inventoryAction.getAction();
			if (action == null) {
				BadBlockHub.log("§cUnknown action set on this object (Position: " + object.getPlace() + " / InventoryName: " + inventoryName + ").");
				return;
			}
			// TODO: do antispam
			String actionData = inventoryAction.getActionData();
			switch (action) {
			case EXECUTE_COMMAND:
				executeCommand(player, action, actionData);
				break;
			case OPEN_INV:
				openInventory(player, action, actionData);
				break;
			case CLOSE_INV:
				closeInventory(player, action, actionData);
				break;
			case TELEPORT_SERVER:
				teleportServer(player, action, actionData);
				break;
			case TELEPORT_LOC:
				teleportLoc(player, action, actionData);
				break;
			case WAITING_LINE:
				waitingLine(player, action, actionData);
				break;
			case NOTHING:
				break;
			default:
				BadBlockHub.log("§cNo action set on this object. (Position: " + object.getPlace() + " / InventoryName: " + inventoryName + ")");
				break;
			}
			break;
		}
	}

	private static void openInventory(BadblockPlayer player, CustomItemAction action, String actionData) {
		String inventoryName = actionData;
		Inventory inventory = BukkitInventories.getInventory(player, inventoryName);
		if (inventory == null) {
			closeInventory(player, action, null);
			return;
		}
		player.closeInventory(); // standby
		player.openInventory(inventory);
		HubPlayer hubPlayer = HubPlayer.get(player);
		hubPlayer.setInventory(inventoryName);
	}

	private static void closeInventory(BadblockPlayer player, CustomItemAction action, String actionData) {
		if (actionData != null && !actionData.isEmpty()) {
			// TODO: do another action ?
		}
		HubPlayer hubPlayer = HubPlayer.get(player);
		hubPlayer.setInventory(null);
		player.closeInventory();
	}

	private static void teleportServer(BadblockPlayer player, CustomItemAction action, String actionData) {
		player.sendPlayer(actionData);
	}

	private static void teleportLoc(BadblockPlayer player, CustomItemAction action, String actionData) {
		Location location = ConfigLoader.getLoc().getLocation(actionData);
		if (location == null) {
			BadBlockHub.log("§cUnknown location name '" + actionData + "'");
			return;
		}
		player.teleport(location);
	}

	private static void executeCommand(BadblockPlayer player, CustomItemAction action, String actionData) {
		player.performCommand(actionData);
	}

	private static void waitingLine(BadblockPlayer player, CustomItemAction action, String actionData) {
		SEntry sentry = new SEntry(player.getName(), actionData, false);
		GameAPI.getAPI().getRabbitSpeaker().sendAsyncUTF8Publisher("networkdocker.sentry.join", BadBlockHub.getInstance().getNotRestrictiveGson().toJson(sentry), 5000, false);
	}

}
