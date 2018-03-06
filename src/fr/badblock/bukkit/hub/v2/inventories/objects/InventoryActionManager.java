package fr.badblock.bukkit.hub.v2.inventories.objects;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryActionManager
{

	public static void handle(BadblockPlayer player, String inventoryName, InventoryItemObject object, InventoryActionType type)
	{
		// No defined type
		if (type == null)
		{
			return;
		}
		
		// No custom inventory
		HubPlayer hubPlayer = HubPlayer.get(player);
		hubPlayer.setCustomInventory(null);
		
		for (InventoryAction inventoryAction : object.getActions())
		{
			if (!inventoryAction.getActionType().equals(type)) continue;
			CustomItemActionType action = inventoryAction.getAction();
			if (action == null)
			{
				BadBlockHub.log("§cUnknown action set on this object (Position: " + object.getPlace() + " / InventoryName: " + inventoryName + ").");
				return;
			}
			// TODO: do antispam
			String actionData = inventoryAction.getActionData();
			if (action.getAction() == null)
			{	
				BadBlockHub.log("§cNo action set on this object. (Position: " + object.getPlace() + " / InventoryName: " + inventoryName + ")");
				return;
			}
			action.work(player, action, actionData);
			break;
		}
	}

}
