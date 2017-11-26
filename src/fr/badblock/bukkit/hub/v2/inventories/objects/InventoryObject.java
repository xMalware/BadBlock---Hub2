package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter public class InventoryObject {

	private String 					i18name;
	private int	  					lines;
	private InventoryItemObject[]	items;
	
}
