package fr.badblock.bukkit.hub.v2.inventories.objects;

import fr.badblock.bukkit.hub.v2.inventories.actions.ItemActionExecuteCommand;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum CustomItemActionType
{

	OPEN_INV			(new ItemActionExecuteCommand()),
	CLOSE_INV			(new ItemActionExecuteCommand()),
	TELEPORT_SERVER		(new ItemActionExecuteCommand()),
	TELEPORT_LOC		(new ItemActionExecuteCommand()),
	EXECUTE_COMMAND		(new ItemActionExecuteCommand()),
	WAITING_LINE		(new ItemActionExecuteCommand()),
	NOTHING				(new ItemActionExecuteCommand())
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
