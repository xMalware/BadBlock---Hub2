package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionModePremium extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		String spamKey = "player_mode_" + player.getName();

		if (GlobalFlags.has(spamKey))
		{
			player.sendTranslatedMessage("hub.mode.pleasewait");
			return;
		}

		// 10s antispam
		GlobalFlags.set(spamKey, 5000);

		if (player.getObject() != null)
		{
			player.saveGameData();
		}
		
		HubStoredPlayer hubStoredPlayer = HubStoredPlayer.get(player);
		
		if (!hubStoredPlayer.isModeSelected())
		{
			hubStoredPlayer.setModeSelected(true);
			hubStoredPlayer.save(player);
		}
		
		player.getObject().addProperty("onlineMode", true);
		
		player.sendTranslatedMessage("hub.mode.nowpremium");
		player.closeInventory();
	}

}
