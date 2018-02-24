package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagPercentLevel extends InventoryTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		double calc = (double) player.getPlayerData().getXp() / (double) player.getPlayerData().getXpUntilNextLevel();
		return Double.toString((double) (calc * 100.0D));
	}

}
