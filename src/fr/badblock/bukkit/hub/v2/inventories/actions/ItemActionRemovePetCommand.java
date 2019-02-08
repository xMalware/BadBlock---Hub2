package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionRemovePetCommand extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{	
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer.getPet() == null)
		{
			player.sendTranslatedMessage("hub.pets.nopet");
			return;
		}
			
		hubPlayer.getPet().undeploy(player);
		hubPlayer.setPet(null);
		player.sendTranslatedMessage("hub.pets.disabled");
	}

}
