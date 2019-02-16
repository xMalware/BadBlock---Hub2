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

	public static Map<String, String> lastKey = new HashMap<>(); 
	public static Map<String, Integer> statIndex = new HashMap<>(); 
	
	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		String data = null;

		for (InventoryAction action : object.getActions())
		{
			if (action.getAction().equals(CustomItemActionType.SHOW_STAT))
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
		
		if (lastKey.containsKey(player.getName().toLowerCase()))
		{
			if (!lastKey.get(player.getName().toLowerCase()).equals(key))
			{
				statIndex.remove(key);
			}
		}
		else
		{
			statIndex.remove(key);
		}
		
		lastKey.put(player.getName().toLowerCase(), key);
		
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