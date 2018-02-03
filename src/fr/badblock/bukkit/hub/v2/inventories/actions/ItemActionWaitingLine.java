package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.sentry.SEntry;

public class ItemActionWaitingLine extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		SEntry sentry = new SEntry(player.getName(), actionData, false);
		GameAPI.getAPI().getRabbitSpeaker().sendAsyncUTF8Publisher("networkdocker.sentry.join", BadBlockHub.getInstance().getNotRestrictiveGson().toJson(sentry), 
				5000, false);
	}

}
