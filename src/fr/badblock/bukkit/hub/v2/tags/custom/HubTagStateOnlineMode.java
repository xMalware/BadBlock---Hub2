package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagStateOnlineMode extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		boolean onlineMode = false;

		if (player.getObject() != null)
		{
			if (player.getObject().has("onlineMode"))
			{
				onlineMode = player.getObject().get("onlineMode").getAsBoolean();
			}
		}
		
		return onlineMode ? player.getTranslatedMessage("hub.tags.mode_online")[0] : player.getTranslatedMessage("hub.tags.mode_offline")[0];
	}

}