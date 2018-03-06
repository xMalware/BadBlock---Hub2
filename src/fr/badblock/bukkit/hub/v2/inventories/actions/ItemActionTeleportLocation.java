package fr.badblock.bukkit.hub.v2.inventories.actions;

import org.bukkit.Location;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionTeleportLocation extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		Location location = ConfigLoader.getLoc().getLocation(actionData);
		if (location == null)
		{
			BadBlockHub.log("§cUnknown location name '" + actionData + "'");
			return;
		}
		player.teleport(location);
	}

}
