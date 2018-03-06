package fr.badblock.bukkit.hub.v2.inventories.objects;

import org.bukkit.event.block.Action;

public enum InventoryActionType
{

	RIGHT_CLICK,
	LEFT_CLICK;

	public static InventoryActionType get(Action action)
	{
		return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK ? LEFT_CLICK 
				: action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK ? RIGHT_CLICK
						: null;
	}

	public static InventoryActionType get(ItemAction action)
	{
		return action == ItemAction.INVENTORY_LEFT_CLICK || action == ItemAction.INVENTORY_DROP ? LEFT_CLICK
				: action == ItemAction.INVENTORY_RIGHT_CLICK ? RIGHT_CLICK
						: null;
	}

}
