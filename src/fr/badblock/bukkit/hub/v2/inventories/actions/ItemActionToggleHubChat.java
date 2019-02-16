package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.inventories.BukkitInventories;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionToggleHubChat extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		String spamKey = "player_togglehubchat_" + player.getName();
		
		if (GlobalFlags.has(spamKey))
		{
			player.sendTranslatedMessage("hub.togglehubchat.pleasewait");
			return;
		}
		
		// 10s antispam
		GlobalFlags.set(spamKey, 1000);
		
		HubStoredPlayer hubStoredPlayer = HubStoredPlayer.get(player);
		
		if (hubStoredPlayer == null)
		{
			return;
		}
		
		if (hubStoredPlayer.isHideHubChat())
		{
			hubStoredPlayer.setHideHubChat(false);
			hubStoredPlayer.save(player);
			player.sendTranslatedMessage("hub.togglehubchat.show");
		}
		else
		{
			hubStoredPlayer.setHideHubChat(true);
			hubStoredPlayer.save(player);
			player.sendTranslatedMessage("hub.togglehubchat.hide");
		}

		player.openInventory(BukkitInventories.getInventory(player, "parametre_1_hub"));
	}

}
