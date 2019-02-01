package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionCloseInventory extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		hubPlayer.setInventory(null);
		hubPlayer.setCustomInventory(null);
		hubPlayer.setCustomActions(null);
		
		player.closeInventory();
	}

}
