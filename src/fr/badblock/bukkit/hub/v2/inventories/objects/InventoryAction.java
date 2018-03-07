package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InventoryAction
{

	private InventoryActionType		actionType;
	private CustomItemActionType	action;
	private String			   		actionData;
	
}
