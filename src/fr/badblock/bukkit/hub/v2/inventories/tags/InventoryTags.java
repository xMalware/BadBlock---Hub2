package fr.badblock.bukkit.hub.v2.inventories.tags;

import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTag;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagBadcoins;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagBadcoinsNeeded;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagLanguage;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagLevelNeeded;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagLevels;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagPercentLevel;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagPlayerName;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagShopPoints;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagShopPointsNeeded;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagXP;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagXPNeededForNextLevel;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum InventoryTags
{

	// Player info
	BADCOINS(new InventoryTagBadcoins(), "{badcoins}"),
	LEVELS(new InventoryTagLevels(), "{levels}"),
	PERCENT_LEVEL(new InventoryTagPercentLevel(), "{percentLevel}"),
	PLAYERNAME(new InventoryTagPlayerName(), "{playerName}"),
	SHOP_POINTS(new InventoryTagShopPoints(), "{shopPoints}"),
	XP(new InventoryTagXP(), "{Xp}"),
	LANGUAGE(new InventoryTagLanguage(), "{Language}"),
	BOOSTER_NUMBER(new InventoryTagLanguage(), "{Booster_Number}"),
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
