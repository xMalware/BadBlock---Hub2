package fr.badblock.bukkit.hub.v2.inventories.objects;

import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionBoosterGameSelect;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionBoosterRemove;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionBuyFeature;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionCloseInventory;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionExecuteCommand;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionExecuteCommandBungee;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionModeCrack;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionModePremium;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionOpenInventory;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionRemoveEffect;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionRemovePet;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionSendLadderCommand;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionSendMessage;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionStatShow;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionTeleportLocation;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionTeleportServer;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionToggleGameMessages;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionToggleHubChat;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionToggleMode;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionToggleParticles;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionTogglePlayers;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionToggleTFA;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionUseFeature;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionWaitingLine;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum CustomItemActionType
{

	OPEN_INV			(new ItemActionOpenInventory()),
	CLOSE_INV			(new ItemActionCloseInventory()),
	TELEPORT_SERVER		(new ItemActionTeleportServer()),
	TELEPORT_LOC		(new ItemActionTeleportLocation()),
	EXECUTE_COMMAND		(new ItemActionExecuteCommand()),
	EXECUTE_COMMANDBUNGEE		(new ItemActionExecuteCommandBungee()),
	WAITING_LINE		(new ItemActionWaitingLine()),
	BUY_FEATURE			(new ItemActionBuyFeature()),
	USE_FEATURE			(new ItemActionUseFeature()),
	SEND_MESSAGE			(new ItemActionSendMessage()),
	SEND_LADDERCOMMAND	(new ItemActionSendLadderCommand()),
	BOOSTER_REMOVE				(new ItemActionBoosterRemove()),
	BOOSTER_GAMESELECT		(new ItemActionBoosterGameSelect()),
	REMOVE_PET							(new ItemActionRemovePet()),
	REMOVE_EFFECT					(new ItemActionRemoveEffect()),
	TOGGLE_PLAYERS 				(new ItemActionTogglePlayers()),
	TOGGLE_PARTICLES (new ItemActionToggleParticles()),
	TOGGLE_GAMEMESSAGES (new ItemActionToggleGameMessages()),
	TOGGLE_HUBCHAT (new ItemActionToggleHubChat()),
	TOGGLE_TFA (new ItemActionToggleTFA()),
	TOGGLE_MODE (new ItemActionToggleMode()),
	MODE_CRACK (new ItemActionModeCrack()),
	MODE_PREMIUM (new ItemActionModePremium()),
	STAT_SHOW (new ItemActionStatShow()),
	NOTHING				(null);

	private CustomItemAction action;

	CustomItemActionType(CustomItemAction action)
	{
		setAction(action);
	}

	public void setAction(CustomItemAction action)
	{
		this.action = action;
	}

	public void work(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		getAction().execute(player, action, actionData);
	}
	
	public static CustomItemActionType fromString(String string)
	{
		for (CustomItemActionType customItemActionType : CustomItemActionType.values())
		{
			if (string.equalsIgnoreCase(customItemActionType.name()))
			{
				return customItemActionType;
			}
		}
		return null;
	}

}
