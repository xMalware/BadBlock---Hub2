package fr.badblock.bukkit.hub.v2.tags;

import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTag;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagBadcoins;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagBadcoinsMultiplier;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagBadcoinsNeeded;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagGamePlayers;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagGroupPrefix;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagGroupSuffix;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagHideGameMessages;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagHideHubChat;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagHideParticles;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagHidePlayers;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagIgnoreNumber;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagLanguage;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagLevelNeeded;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagLevels;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagLocalOnlinePlayers;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagNetworkOnlinePlayers;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagOwned;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagPercentLevel;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagPing;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagPlayerName;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagServerId;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagServerName;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagShopPoints;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagShopPointsNeeded;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagStat;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagStateOnlineMode;
import fr.badblock.bukkit.hub.v2.tags.custom.HubTagStateTFA;
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
	GAME_PLAYERS(new HubTagGamePlayers(), "{Game_OnlinePlayers}"),
	// Price
	OWNED(new HubTagOwned(), "{owned}"),
	BADCOINS_NEEDED(new HubTagBadcoinsNeeded(), "{badcoinsNeeded}"),
	LEVEL_NEEDED(new HubTagLevelNeeded(), "{levelNeeded}"),
	SHOPPOINTS_NEEDED(new HubTagShopPointsNeeded(), "{shopPointsNeeded}"),
	XP_NEEDED_FOR_NEXT_LEVEL(new HubTagXPNeededForNextLevel(), "{XpNeededforNextLevel}"),
	// PARAMETERS
	HIDE_HUBCHAT(new HubTagHideHubChat(), "{hide_hubchat}"),
	HIDE_GAMEMESSAGES(new HubTagHideGameMessages(), "{hide_gamemessages}"),
	HIDE_PARTICLES(new HubTagHideParticles(), "{hide_particles}"),
	HIDE_PLAYERS(new HubTagHidePlayers(), "{hide_players}"),
	STATE_TFA(new HubTagStateTFA(), "{state_tfa}"),
	STATE_MODE(new HubTagStateOnlineMode(), "{state_mode}"),
	STAT(new HubTagStat(), "{stat}");
	
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
