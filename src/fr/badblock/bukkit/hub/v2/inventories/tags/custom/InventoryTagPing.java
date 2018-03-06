package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagPing extends InventoryTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return Integer.toString(((CraftPlayer) player).getHandle().ping);
	}

}
