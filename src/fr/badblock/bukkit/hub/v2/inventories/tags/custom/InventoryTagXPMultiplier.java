package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagXPMultiplier extends InventoryTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return Double.toString(player.getPlayerData().getXpMultiplier());
	}

}
