package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter public class InventoryItemObject
{

	private String				i18name;
	private String				i18lore;
	private int    			 	place;
	private int				 	amount = 1;
	private String 			 	type;
	private InventoryAction[]	actions;
	private boolean				fakeEnchant;
	
	public int getPlace()
	{
		return place;
	}
	
}
