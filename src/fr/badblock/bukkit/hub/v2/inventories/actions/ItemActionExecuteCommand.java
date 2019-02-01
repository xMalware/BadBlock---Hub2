package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionExecuteCommand extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		// In order to avoid conflicts with inventory-opener commands
		player.closeInventory();

		HubPlayer hubPlayer = HubPlayer.get(player);

		if (hubPlayer != null)
		{
			hubPlayer.setInventory(null);
			hubPlayer.setCustomInventory(null);
			hubPlayer.setCustomActions(null);
		}

		player.performCommand(actionData);
	}

}
