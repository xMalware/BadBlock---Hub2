package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagPercentLevel extends InventoryTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player) {
		double calc = (double) player.getPlayerData().getXp() / (double) player.getPlayerData().getXpUntilNextLevel();
		calc *= 100;
		return Double.toString(calc);
	}

}
