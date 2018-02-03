package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.Getter;

@Getter
public class InventoryAction
{

	private InventoryActionType actionType;
	private CustomItemAction	action;
	private String			   	actionData;
	
	public CustomItemAction getAction()
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
