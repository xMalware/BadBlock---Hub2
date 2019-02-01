package fr.badblock.bukkit.hub.v2.inventories.actions;

import org.bukkit.inventory.Inventory;

import fr.badblock.bukkit.hub.v2.inventories.BukkitInventories;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.data.PlayerData;

public class ItemActionBoosterRemove extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{

		int index = Integer.parseInt(actionData);

		PlayerData data = player.getPlayerData();

		if (data == null)
		{
			player.sendTranslatedMessage("hub.boosters.unknownbooster");
			return;
		}

		if (data.getBoosters() == null || data.getBoosters().isEmpty())
		{
			player.sendTranslatedMessage("hub.boosters.unknownbooster");
			return;
		}

		if (data.getBoosters().size() <= index)
		{
			player.sendTranslatedMessage("hub.boosters.unknownbooster");
			return;
		}

		data.getBoosters().remove(index);
		player.sendTranslatedMessage("hub.boosters.removed");
		
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer.getInventory() == null)
		{
			return;
		}
		
		Inventory inventory = BukkitInventories.getInventory(player, hubPlayer.getInventory());
		if (inventory == null)
		{
			CustomItemActionType.CLOSE_INV.work(player, action, null);
			return;
		}
		
		player.openInventory(inventory);
		hubPlayer.setInventory(hubPlayer.getInventory());
	}

}
