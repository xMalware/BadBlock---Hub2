package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public abstract class HubTag
{

	public abstract String getTag(BadblockPlayer player, InventoryItemObject object);
	
	public abstract String getTag(BadblockPlayer player);

}
