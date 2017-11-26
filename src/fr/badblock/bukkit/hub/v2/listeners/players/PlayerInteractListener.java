package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionManager;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerInteractListener extends BadListener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		event.setCancelled(!player.hasAdminMode());
		// default inventory
		ItemStack handItem = player.getInventory().getItemInHand();
		InventoryActionType actionType = InventoryActionType.get(event.getAction());
		if (handItem != null) {
			InventoryObject defaultInventory = InventoriesLoader.getDefaultInventory();
			if (defaultInventory != null) {
				InventoryItemObject itemObject = null;
				for (InventoryItemObject item : defaultInventory.getItems()) {
					if (player.getInventory().getHeldItemSlot() == item.getPlace()) {
						itemObject = item;
						break;
					}
				}
				if (itemObject != null) InventoryActionManager.handle(player, InventoriesLoader.getConfig().getJoinDefaultInventory(), itemObject, actionType);
			}
		}
	}
	
}
