package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagServerId extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object)
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		String serverName = GameAPI.getServerName();
		String[] splitter = serverName.split("_");
		if (splitter.length != 2)
		{
			return Integer.toString(-1);
		}
		try
		{
			return Integer.toString(Integer.parseInt(splitter[1]));
		}
		catch (Exception error)
		{
			error.printStackTrace();
			return Integer.toString(-1);
		}
	}

}
