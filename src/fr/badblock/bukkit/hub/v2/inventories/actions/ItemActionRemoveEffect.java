package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionRemoveEffect extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{	
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer.getEffect() == null || hubPlayer.getEffect().isDone())
		{
			player.sendTranslatedMessage("hub.effects.noeffect");
			return;
		}
			
		hubPlayer.getEffect().cancel();
		hubPlayer.setEffect(null);
		player.sendTranslatedMessage("hub.effects.disabled");
	}

}
