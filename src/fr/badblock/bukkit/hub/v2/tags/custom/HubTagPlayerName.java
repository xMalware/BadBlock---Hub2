package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagPlayerName extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object)
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return player.getName();
	}

}
