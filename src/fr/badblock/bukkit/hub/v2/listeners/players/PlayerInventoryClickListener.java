package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionManager;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.ItemAction;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerInventoryClickListener extends BadListener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		BadblockPlayer player = (BadblockPlayer) event.getWhoClicked();
		event.setCancelled(!player.hasAdminMode());
		// default inventory
		ItemStack itemStack = event.getCurrentItem();
		ItemAction itemAction = ItemAction.get(event.getAction());
		InventoryActionType actionType = InventoryActionType.get(itemAction);
		// Hub player
		HubPlayer hubPlayer = HubPlayer.get(player);
		if (itemStack != null) {
			String inventoryName = hubPlayer.getInventory();
			boolean done = false;
			if (inventoryName != null && !inventoryName.isEmpty() && event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
				InventoryObject inventory = InventoriesLoader.getInventory(inventoryName);
				if (inventory != null) {
					InventoryItemObject itemObject = null;
					for (InventoryItemObject item : inventory.getItems()) {
						if (event.getSlot() == item.getPlace()) {
							itemObject = item;
							done = true;
							break;
						}
					}
					if (itemObject != null) InventoryActionManager.handle(player, inventoryName, itemObject, actionType);
				}
			}
			if (!done) {
				InventoryObject defaultInventory = InventoriesLoader.getDefaultInventory();
				if (defaultInventory != null) {
					InventoryItemObject itemObject = null;
					for (InventoryItemObject item : defaultInventory.getItems()) {
						if (event.getSlot() == item.getPlace()) {
							itemObject = item;
							done = true;
							break;
						}
					}
					if (itemObject != null) InventoryActionManager.handle(player, InventoriesLoader.getConfig().getJoinDefaultInventory(), itemObject, actionType);
				}
			}
		}
	}

}
