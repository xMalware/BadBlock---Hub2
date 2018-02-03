package fr.badblock.bukkit.hub.v2.inventories.objects;

import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionCloseInventory;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionExecuteCommand;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionOpenInventory;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionTeleportLocation;
import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionTeleportServer;
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
	WAITING_LINE		(new ItemActionWaitingLine()),
	NOTHING				(null)
	/*
	 * TODO for cosmetics!
	 * BUY_FEATURE
	 * USE_FEATURE
	 */;

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

}
