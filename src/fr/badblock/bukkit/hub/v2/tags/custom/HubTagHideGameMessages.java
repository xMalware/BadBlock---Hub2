package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagHideGameMessages extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		HubStoredPlayer hubStoredPlayer = HubStoredPlayer.get(player);

		boolean hide = hubStoredPlayer.isHideGameMessages();
		return hide ? player.getTranslatedMessage("hub.tags.hidegamemessages")[0] : player.getTranslatedMessage("hub.tags.showgamemessages")[0];
	}

}