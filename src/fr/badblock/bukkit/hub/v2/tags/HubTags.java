package fr.badblock.bukkit.hub.v2.tags;

import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTag;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagBadcoins;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagBadcoinsMultiplier;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagBadcoinsNeeded;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagGroupPrefix;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagGroupSuffix;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagIgnoreNumber;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagLanguage;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagLevelNeeded;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagLevels;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagOwned;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagPercentLevel;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagPing;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagPlayerName;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagServerId;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagServerName;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagShopPoints;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagShopPointsNeeded;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagXP;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagXPMultiplier;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagXPNeededForNextLevel;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum HubTags
{

	// Player info
	PLAYER_NAME(new HubTagPlayerName(), "{PlayerName}"),
	BADCOINS(new HubTagBadcoins(), "{badcoins}"),
	BADCOINS_MULTIPLIER(new HubTagBadcoinsMultiplier(), "{badcoins_multiplier}"),
	LEVELS(new HubTagLevels(), "{levels}"),
	PERCENT_LEVEL(new HubTagPercentLevel(), "{percentLevel}"),
	PLAYERNAME(new HubTagPlayerName(), "{playerName}"),
	SHOP_POINTS(new HubTagShopPoints(), "{shopPoints}"),
	XP(new HubTagXP(), "{Xp}"),
	XP_MULTIPLIER(new HubTagXPMultiplier(), "{Xp_Multiplier}"),
	LANGUAGE(new HubTagLanguage(), "{Language}"),
	BOOSTER_NUMBER(new HubTagLanguage(), "{Booster_Number}"),
	IGNORE_NUMBER(new HubTagIgnoreNumber(), "{Ignore_Number}"),
	PING(new HubTagPing(), "{Ping}"),
	GROUP_PREFIX(new HubTagGroupPrefix(), "{Group_Prefix}"),
	GROUP_SUFFIX(new HubTagGroupSuffix(), "{Group_Suffix}"),
	SERVER_NAME(new HubTagServerName(), "{Server_Name}"),
	SERVER_ID(new HubTagServerId(), "{Server_Id}"),
	// Price
	OWNED(new HubTagOwned(), "{owned}"),
	BADCOINS_NEEDED(new HubTagBadcoinsNeeded(), "{badcoinsNeeded}"),
	LEVEL_NEEDED(new HubTagLevelNeeded(), "{levelNeeded}"),
	SHOPPOINTS_NEEDED(new HubTagShopPointsNeeded(), "{shopPointsNeeded}"),
	XP_NEEDED_FOR_NEXT_LEVEL(new HubTagXPNeededForNextLevel(), "{XpNeededforNextLevel}");
	
	@Setter private HubTag 			hubTag;
	@Setter private List<String>	tags;
	
	HubTags(HubTag hubTag, String... tags)
	{
		setHubTag(hubTag);
		setTags(Arrays.asList(tags));
	}
	
	public String replace(BadblockPlayer player, String string, String tag, InventoryItemObject inventoryItemObject)
	{
		String newTag = getHubTag().getTag(player, inventoryItemObject);
		if (newTag == null)
		{
			return string;
		}
		// Return replaced string with tag
		return string.replace(tag, newTag);
	}
	
	public String replace(BadblockPlayer player, String string, String tag)
	{
		String newTag = getHubTag().getTag(player);
		if (newTag == null)
		{
			return string;
		}
		// Return replaced string with tag
		return string.replace(tag, newTag);
	}
	
}