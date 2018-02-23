package fr.badblock.bukkit.hub.v2.inventories.tags;

import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTag;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagPlayerName;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum InventoryTags
{

	PLAYERNAME(new InventoryTagPlayerName(), "{playerName}");
	
	@Setter private InventoryTag 	inventoryTag;
	@Setter private List<String>	tags;
	
	InventoryTags(InventoryTag inventoryTag, String... tags)
	{
		setInventoryTag(inventoryTag);
		setTags(Arrays.asList(tags));
	}
	
	public String replace(BadblockPlayer player, String string, String tag, InventoryObject inventoryObject)
	{
		// Return replaced string with tag
		return string.replace(tag, getInventoryTag().getTag(player, inventoryObject));
	}
	
}
