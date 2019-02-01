package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.api.common.utils.FullSEntry;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.rabbit.SEntryInfosListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagGamePlayers extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object)
	{
		if (object == null)
		{
			return getTag(player);
		}

		String queueName = "";

		for (InventoryAction inventoryAction : object.getActions())
		{
			if (inventoryAction.getAction().equals(CustomItemActionType.TELEPORT_SERVER) || 
					inventoryAction.getAction().equals(CustomItemActionType.WAITING_LINE))
			{
				queueName = inventoryAction.getActionData();
			}
		}
		
		if (!SEntryInfosListener.sentries.containsKey(queueName))
		{
			return "?";
		}
		
		FullSEntry sentry = SEntryInfosListener.sentries.get(queueName);
		int i = sentry.getIngamePLayers() + sentry.getWaitinglinePlayers();
		
		return Integer.toString(i);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return "??";
	}

}
