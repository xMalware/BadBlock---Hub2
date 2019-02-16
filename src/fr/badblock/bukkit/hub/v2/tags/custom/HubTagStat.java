package fr.badblock.bukkit.hub.v2.tags.custom;

import java.util.HashMap;
import java.util.Map;

import fr.badblock.api.common.utils.general.MathUtils;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagStat extends HubTag
{

	Map<String, Integer> statIndex = new HashMap<>(); 
	
	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		String data = null;

		for (InventoryAction action : object.getActions())
		{
			if (action.getAction().equals(CustomItemActionType.STAT_SHOW))
			{
				data = action.getActionData();
			}
		}

		if (data == null)
		{
			return "?";
		}
		
		String[] splitter = data.split(",");
		String serverName = splitter[0];
		
		String key = player.getName() + "_" + object.toString();
		
		int index = 1;
		if (statIndex.containsKey(key))
		{
			index = statIndex.get(key);
		}
		
		statIndex.put(key, index + 1);
		
		return MathUtils.round(player.getPlayerData().getStatistics(serverName, splitter[index]), 2) + "";
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return "unknown";
	}

}