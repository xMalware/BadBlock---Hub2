package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.Getter;

@Getter public class InventoryAction {

	private InventoryActionType actionType;
	private CustomItemAction	action;
	private String			   	actionData;

}
