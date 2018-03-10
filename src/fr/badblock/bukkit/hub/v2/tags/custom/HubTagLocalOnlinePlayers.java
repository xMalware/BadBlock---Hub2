package fr.badblock.bukkit.hub.v2.tags.custom;

import org.bukkit.Bukkit;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagLocalOnlinePlayers extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object)
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return Integer.toString(Bukkit.getOnlinePlayers().size());
	}

}
