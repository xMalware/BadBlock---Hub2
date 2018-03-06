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

public class PlayerInventoryClickListener extends BadListener
{

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (!(event.getWhoClicked() instanceof Player))
		{
			return;
		}
		BadblockPlayer player = (BadblockPlayer) event.getWhoClicked();
		// Set cancelled
		event.setCancelled(!player.hasAdminMode());
		// Get basic objects
		ItemStack itemStack = event.getCurrentItem();
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		// Work with custom inventories in priority
		if (workCustomInventories(player, hubPlayer, itemStack))
		{
			return;
		}
		
		// Work with basic inventories
		workBasicInventories(player, hubPlayer, itemStack, event);
	}
	
	private boolean workCustomInventories(BadblockPlayer player, HubPlayer hubPlayer, ItemStack itemStack)
	{
		if (hubPlayer.getCustomInventory() != null)
		{
			return hubPlayer.getCustomInventory().work(player, itemStack);
		}
		return false;
	}

	private boolean workBasicInventories(BadblockPlayer player, HubPlayer hubPlayer, ItemStack itemStack, InventoryClickEvent event)
	{
		ItemAction itemAction = ItemAction.get(event.getAction());
		InventoryActionType actionType = InventoryActionType.get(itemAction);
		if (itemStack != null)
		{
			String inventoryName = hubPlayer.getInventory();
			// Handling custom inventories
			if (inventoryName != null && !inventoryName.isEmpty() && event.getClickedInventory().getType().equals(InventoryType.CHEST))
			{
				return handleInventoryManager(event, player, inventoryName, actionType, InventoriesLoader.getInventory(inventoryName));
			}
			// Default join inventory handling
			return handleInventoryManager(event, player, InventoriesLoader.getConfig().getJoinDefaultInventory(), actionType, InventoriesLoader.getDefaultInventory());
		}
		return false;
	}

	private boolean handleInventoryManager(InventoryClickEvent event, BadblockPlayer player, String inventoryName, InventoryActionType actionType, InventoryObject inventoryObject)
	{
		boolean done = false;
		if (inventoryObject == null)
		{
			return false;
		}
		InventoryItemObject itemObject = null;
		for (InventoryItemObject item : inventoryObject.getItems())
		{
			if (event.getSlot() == item.getPlace())
			{
				done = true;
				itemObject = item;
				break;
			}
		}
		if (itemObject != null)
		{
			InventoryActionManager.handle(player, inventoryName, itemObject, actionType);
		}
		return done;
	}

}