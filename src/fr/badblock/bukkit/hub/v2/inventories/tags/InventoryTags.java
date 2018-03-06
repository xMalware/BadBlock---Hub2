package fr.badblock.bukkit.hub.v2.inventories.tags;

import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTag;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagBadcoins;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagBadcoinsMultiplier;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagBadcoinsNeeded;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagGroupPrefix;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagGroupSuffix;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagIgnoreNumber;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagLanguage;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagLevelNeeded;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagLevels;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagOwned;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagPercentLevel;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagPing;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagPlayerName;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagShopPoints;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagShopPointsNeeded;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagXP;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagXPMultiplier;
import fr.badblock.bukkit.hub.v2.inventories.tags.custom.InventoryTagXPNeededForNextLevel;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum InventoryTags
{

	// Player info
	PLAYER_NAME(new InventoryTagPlayerName(), "{PlayerName}"),
	BADCOINS(new InventoryTagBadcoins(), "{badcoins}"),
	BADCOINS_MULTIPLIER(new InventoryTagBadcoinsMultiplier(), "{badcoins_multiplier}"),
	LEVELS(new InventoryTagLevels(), "{levels}"),
	PERCENT_LEVEL(new InventoryTagPercentLevel(), "{percentLevel}"),
	PLAYERNAME(new InventoryTagPlayerName(), "{playerName}"),
	SHOP_POINTS(new InventoryTagShopPoints(), "{shopPoints}"),
	XP(new InventoryTagXP(), "{Xp}"),
	XP_MULTIPLIER(new InventoryTagXPMultiplier(), "{Xp_Multiplier}"),
	LANGUAGE(new InventoryTagLanguage(), "{Language}"),
	BOOSTER_NUMBER(new InventoryTagLanguage(), "{Booster_Number}"),
	IGNORE_NUMBER(new InventoryTagIgnoreNumber(), "{Ignore_Number}"),
	PING(new InventoryTagPing(), "{Ping}"),
	GROUP_PREFIX(new InventoryTagGroupPrefix(), "{Group_Prefix}"),
	GROUP_SUFFIX(new InventoryTagGroupSuffix(), "{Group_Suffix}"),
	// Price
	OWNED(new InventoryTagOwned(), "{owned}"),
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
	
	public String replace(BadblockPlayer player, String string, String tag)
	{
		String newTag = getInventoryTag().getTag(player);
		if (newTag == null)
		{
			return string;
		}
		// Return replaced string with tag
		return string.replace(tag, newTag);
	}
	
}
