package fr.badblock.bukkit.hub.v2.inventories;

import java.util.Map;

import org.bukkit.inventory.Inventory;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public abstract class InventoryFiller
{
	
	public static void registerCustomActions(BadblockPlayer player, Map<Integer, InventoryAction[]> actions)
	{
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		hubPlayer.setCustomActions(actions);
	}
	
	public abstract void fill(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory);
	
}
