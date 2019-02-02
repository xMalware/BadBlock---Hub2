package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
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

			if (player.getOpenInventory() instanceof PlayerInventory)
			{
				// Default join inventory handling
				return handleInventoryManager(event, player, InventoriesLoader.getConfig().getJoinDefaultInventory(), actionType, InventoriesLoader.getDefaultInventory());
			}
			
			return false;
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
			if (event.getSlot() != item.getPlace())
			{
				continue;
			}

			done = true;
			itemObject = item;
			break;
		}

		if (itemObject != null)
		{
			InventoryActionManager.handle(player, inventoryName, itemObject, actionType);
			return done;
		}

		HubPlayer hubPlayer = HubPlayer.get(player);

		if (hubPlayer.getCustomActions() == null || hubPlayer.getCustomActions().isEmpty())
		{
			return done;
		}

		if (!hubPlayer.getCustomActions().containsKey(event.getSlot()))
		{
			return done;
		}

		InventoryAction[] inventoryActions = hubPlayer.getCustomActions().get(event.getSlot());

		for (InventoryAction inventoryAction : inventoryActions)
		{
			if (!inventoryAction.getActionType().equals(actionType))
			{
				continue;
			}

			CustomItemActionType action = inventoryAction.getAction();
			if (action == null)
			{
				BadBlockHub.log("§cUnknown action set on this custom object (Filled item / Position: " + event.getSlot() + " / InventoryName: " + inventoryName + ").");
				return done;
			}

			String actionData = inventoryAction.getActionData();
			if (action.getAction() == null)
			{	
				BadBlockHub.log("§cNo action set on this object. (Filled item / Position: " + event.getSlot() + " / InventoryName: " + inventoryName + ")");
				return done;
			}

			action.work(player, action, actionData);
			done = true;

			break;
		}

		return done;
	}

}