package fr.badblock.bukkit.hub.v2.inventories;

import org.bukkit.inventory.Inventory;

import fr.badblock.bukkit.hub.v2.inventories.fillers.InventoryFillerBooster;
import fr.badblock.bukkit.hub.v2.inventories.fillers.InventoryFillerHostList;
import fr.badblock.bukkit.hub.v2.inventories.fillers.InventoryFillerHubList;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public enum InventoryFillers
{

	SHOW_BOOSTERS(new InventoryFillerBooster()),
	SHOW_HUB_LIST(new InventoryFillerHubList()),
	SHOW_HOST_LIST(new InventoryFillerHostList());
	
	private InventoryFiller filler;
	
	InventoryFillers (InventoryFiller filler)
	{
		this.filler = filler;
	}
	
	public static InventoryFillers getFiller(String rawFillerName)
	{
		for (InventoryFillers filler : InventoryFillers.values())
		{
			if (filler.name().equalsIgnoreCase(rawFillerName))
			{
				return filler;
			}
		}
		
		throw new IllegalArgumentException("Unknown filler name : " + rawFillerName);
	}

	public void fill(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		this.filler.fill(player, inventoryObject, inventory);
	}
	
}