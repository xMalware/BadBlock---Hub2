package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.Getter;

@Getter
public class InventoryAction
{

	private InventoryActionType actionType;
	private CustomItemActionType	action;
	private String			   	actionData;
	
	public CustomItemActionType getAction()
	{
		return action;
	}

	public Object getActionType()
	{
		return actionType;
	}

	public String getActionData()
	{
		return actionData;
	}

}
