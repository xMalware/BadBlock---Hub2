package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagIgnoreNumber extends InventoryTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return Double.toString(player.getPlayerData().getIgnoreList().size());
	}

}