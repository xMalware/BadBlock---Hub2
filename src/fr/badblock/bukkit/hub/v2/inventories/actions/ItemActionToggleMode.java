package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionToggleMode extends CustomItemAction
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

		boolean onlineMode = false;

		if (player.getObject() != null)
		{
			if (player.getObject().has("onlineMode"))
			{
				onlineMode = player.getObject().get("onlineMode").getAsBoolean();
			}
		}

		if (!onlineMode)
		{
			player.getObject().addProperty("onlineMode", true);
			player.saveGameData();
			player.sendTranslatedMessage("hub.mode.nowpremium");
			return;
		}

		player.getObject().addProperty("onlineMode", false);
		player.saveGameData();
		player.sendTranslatedMessage("hub.mode.nowcrack");
	}

}