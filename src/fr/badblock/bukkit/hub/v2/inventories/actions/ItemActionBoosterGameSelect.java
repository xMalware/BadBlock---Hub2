package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.data.PlayerData;
import fr.badblock.gameapi.players.data.boosters.PlayerBooster;

public class ItemActionBoosterGameSelect extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		int index = Integer.parseInt(actionData);

		PlayerData data = player.getPlayerData();

		if (data == null)
		{
			player.sendTranslatedMessage("hub.boosters.unknownbooster");
			return;
		}

		if (data.getBoosters() == null || data.getBoosters().isEmpty())
		{
			player.sendTranslatedMessage("hub.boosters.unknownbooster");
			return;
		}

		if (data.getBoosters().size() <= index)
		{
			player.sendTranslatedMessage("hub.boosters.unknownbooster");
			return;
		}

		PlayerBooster playerBooster = data.getBoosters().get(index);

		if (playerBooster == null)
		{
			player.sendTranslatedMessage("hub.boosters.unknownbooster");
			return;
		}

		if (playerBooster.isExpired())
		{
			player.sendTranslatedMessage("hub.boosters.boosterexpired");
			return;
		}

		if (!playerBooster.isEnabled())
		{
			player.sendTranslatedMessage("hub.boosters.boosteralreadyenabled");
			return;
		}

		// TODO booster enabling: game choose inventory..
		player.closeInventory();
		player.sendMessage("TODO");
	}

}
