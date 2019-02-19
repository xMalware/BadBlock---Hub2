package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagGamePoints extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		if (!ConfigLoader.getGameHub().isEnabled())
		{
			return "?";
		}
		
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer.getGamePoints() == -1)
		{
			return "...";
		}
		
		return Long.toString(hubPlayer.getGamePoints());
	}

}
