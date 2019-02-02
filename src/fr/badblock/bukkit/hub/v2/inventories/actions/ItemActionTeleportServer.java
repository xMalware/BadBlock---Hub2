package fr.badblock.bukkit.hub.v2.inventories.actions;

import org.bukkit.Bukkit;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionTeleportServer extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		if (Bukkit.getServerName().equalsIgnoreCase(actionData))
		{
			player.sendTranslatedMessage("hub.errors.alreadyonthisserver");
			return;
		}

		player.sendPlayer(actionData);
	}

}
