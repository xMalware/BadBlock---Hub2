package fr.badblock.bukkit.hub.v2.tags;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

public class TagManager
{

	@Getter private static TagManager instance = new TagManager();

	public String tagify(BadblockPlayer player, String string)
	{
		for (HubTags hubTag : HubTags.values())
		{
			for (String tag : hubTag.getTags())
			{
				if (string.contains(tag))
				{
					string = hubTag.replace(player, string, tag);
				}
			}
		}
		return string;
	}
	
	public String[] tagify(BadblockPlayer player, String... strings)
	{
		for (int i = 0; i < strings.length; i++)
		{
			strings[i] = tagify(player, strings[i]);
		}
		return strings;
	}
	
	public String tagify(BadblockPlayer player, String string, InventoryItemObject itemInventoryObject)
	{
		for (HubTags hubTag : HubTags.values())
		{
			for (String tag : hubTag.getTags())
			{
				if (string.contains(tag))
				{
					string = hubTag.replace(player, string, tag, itemInventoryObject);
				}
			}
		}
		return string;
	}
	
	public String[] tagify(BadblockPlayer player, InventoryItemObject itemInventoryObject, String... strings)
	{
		for (int i = 0; i < strings.length; i++)
		{
			strings[i] = tagify(player, strings[i], itemInventoryObject);
		}
		return strings;
	}
	
}
