package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public abstract class InventoryTag
{
	
	public abstract String getTag(BadblockPlayer player, InventoryItemObject object);

}
