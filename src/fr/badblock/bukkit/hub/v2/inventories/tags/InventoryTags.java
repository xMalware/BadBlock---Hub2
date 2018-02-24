package fr.badblock.bukkit.hub.v2.inventories.tags;

import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.*;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum InventoryTags
{

	// Badcoins
	BADCOINS(new InventoryTagBadcoins(), "{badcoins}"),
	// Levels
	LEVELS(new InventoryTagLevels(), "{levels}"),
	// Percent Levels
	PERCENT_LEVEL(new InventoryTagPercentLevel(), "{percentLevel}"),
	// Player name
	PLAYERNAME(new InventoryTagPlayerName(), "{playerName}"),
	// Shop Points
	SHOP_POINTS(new InventoryTagShopPoints(), "{shopPoints}"),
	// XP
	XP(new InventoryTagXP(), "{Xp}"),
	// Price
	BADCOINS_NEEDED(new InventoryTagBadcoinsNeeded(), "{badcoinsNeeded}"),
	LEVEL_NEEDED(new InventoryTagLevelNeeded(), "{levelNeeded}"),
	SHOPPOINTS_NEEDED(new InventoryTagShopPointsNeeded(), "{shopPointsNeeded}"),
	XP_NEEDED_FOR_NEXT_LEVEL(new InventoryTagXPNeededForNextLevel(), "{XpNeededforNextLevel}");
	
	
	
	@Setter private InventoryTag 	inventoryTag;
	@Setter private List<String>	tags;
	
	InventoryTags(InventoryTag inventoryTag, String... tags)
	{
		setInventoryTag(inventoryTag);
		setTags(Arrays.asList(tags));
	}
	
	public String replace(BadblockPlayer player, String string, String tag, InventoryItemObject inventoryItemObject)
	{
		String newTag = getInventoryTag().getTag(player, inventoryItemObject);
		if (newTag == null)
		{
			return string;
		}
		// Return replaced string with tag
		return string.replace(tag, newTag);
	}
	
}
