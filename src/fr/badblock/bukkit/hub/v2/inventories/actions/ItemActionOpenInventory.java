package fr.badblock.bukkit.hub.v2.inventories.actions;

import org.bukkit.inventory.Inventory;

import fr.badblock.bukkit.hub.v2.inventories.BukkitInventories;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionOpenInventory extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		String inventoryName = actionData;
		Inventory inventory = BukkitInventories.getInventory(player, inventoryName);
		if (inventory == null)
		{
			CustomItemActionType.CLOSE_INV.work(player, action, null);
			return;
		}
		player.closeInventory(); // standby
		player.openInventory(inventory);
		HubPlayer hubPlayer = HubPlayer.get(player);
		hubPlayer.setInventory(inventoryName);
	}

}
