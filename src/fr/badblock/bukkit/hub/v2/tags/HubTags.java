package fr.badblock.bukkit.hub.v2.tags;

import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.tags.custom.*;
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
	// Server info
	SERVER_NAME(new HubTagServerName(), "{Server_Name}"),
	SERVER_ID(new HubTagServerId(), "{Server_Id}"),
	LOCAL_ONLINEPLAYERS(new HubTagLocalOnlinePlayers(), "{Local_OnlinePlayers}"),
	NETWORK_ONLINEPLAYERS(new HubTagNetworkOnlinePlayers(), "{Network_OnlinePlayers}"),
	// Price
	OWNED(new HubTagOwned(), "{owned}"),
	BADCOINS_NEEDED(new HubTagBadcoinsNeeded(), "{badcoinsNeeded}"),
	LEVEL_NEEDED(new HubTagLevelNeeded(), "{levelNeeded}"),
	SHOPPOINTS_NEEDED(new HubTagShopPointsNeeded(), "{shopPointsNeeded}"),
	XP_NEEDED_FOR_NEXT_LEVEL(new HubTagXPNeededForNextLevel(), "{XpNeededforNextLevel}"),
	// Games info (ex: Spleef etc in the Hub)
    GAME_SPLEEF_TIMER_NUMBER(new HubTagSpleefTimerNumber(), "{spleefTimer}"),
	GAME_VIPCOURSE_TIMER_LAUNCHING(new HubTagVIPCourseLaunchingTimerNumber(), "{vipcourseLaunchingTimer}"),
	GAME_VIPCOURSE_TIMER_BEFORE_LAUNCHING_NUMBER(new HubTagVIPCourseBeforeLaunchingTimerNumber, "{vipcourseBeforeLaunchingTimer}");
	
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
