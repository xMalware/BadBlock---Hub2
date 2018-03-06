package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.actions.CommandFactory;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionSendLadderCommand extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{	
		CommandFactory commandFactory = new CommandFactory(player.getName(), actionData);
		GameAPI.getAPI().getRabbitSpeaker().sendAsyncUTF8Publisher("forcecommand.ladder", GameAPI.getGson().toJson(commandFactory), 10000, false);
	}

}
