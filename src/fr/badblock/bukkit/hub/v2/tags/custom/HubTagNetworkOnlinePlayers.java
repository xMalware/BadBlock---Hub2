package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.utils.NetworkUtils;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagNetworkOnlinePlayers extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object)
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return Integer.toString(NetworkUtils.getInstance().getOnlinePlayers());
	}

}
