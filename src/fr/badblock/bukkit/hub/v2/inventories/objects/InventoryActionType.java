package fr.badblock.bukkit.hub.v2.inventories.objects;

import java.util.Arrays;
import java.util.List;

import org.bukkit.event.block.Action;

import lombok.Getter;

@Getter
public enum InventoryActionType
{

	LEFT_CLICK	(new Action[] { Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK }, new ItemAction[] { ItemAction.INVENTORY_LEFT_CLICK, ItemAction.INVENTORY_DROP }),
	RIGHT_CLICK	(new Action[] { Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK }, new ItemAction[] { ItemAction.INVENTORY_RIGHT_CLICK });

	private List<Action>		actions;
	private List<ItemAction>	itemActions;
	
	InventoryActionType(Action[] actions, ItemAction[] itemActions)
	{
		this.actions		= Arrays.asList(actions);
		this.itemActions	= Arrays.asList(itemActions);
	}
	
	public static InventoryActionType fromString(String string)
	{
		for (InventoryActionType inventoryActionType : values())
		{
			if (string.equalsIgnoreCase(inventoryActionType.name()))
			{
				return inventoryActionType;
			}
		}
		return null;
	}
	
	public static InventoryActionType get(Action action)
	{
		for (InventoryActionType inventoryActionType : values())
		{
			if (inventoryActionType.getActions().contains(action))
			{
				return inventoryActionType;
			}
		}
		return null;
	}	

	public static InventoryActionType get(ItemAction itemAction)
	{
		for (InventoryActionType inventoryActionType : values())
		{
			if (inventoryActionType.getItemActions().contains(itemAction))
			{
				return inventoryActionType;
			}
		}
		return null;
	}

}