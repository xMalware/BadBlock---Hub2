package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.api.common.minecraft.BungeePlayerRequest;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionExecuteCommandBungee extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		// In order to avoid conflicts with inventory-opener commands
		player.closeInventory();

		String realName = player.getRealName() != null && !player.getRealName().isEmpty() ? player.getRealName() : player.getName();
		new BungeePlayerRequest(realName, "FORCE_COMMAND", actionData).send(GameAPI.getAPI().getRabbitService());
	}

}
