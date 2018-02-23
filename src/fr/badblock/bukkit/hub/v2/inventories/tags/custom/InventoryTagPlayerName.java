package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagPlayerName extends InventoryTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryObject object)
	{
		return player.getName();
	}

}
