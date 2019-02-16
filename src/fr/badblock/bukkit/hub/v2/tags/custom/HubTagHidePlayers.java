package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagHidePlayers extends HubTag
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

		boolean hide = hubStoredPlayer.isHidePlayers();
		return hide ? player.getTranslatedMessage("hub.tags.hideplayers")[0] : player.getTranslatedMessage("hub.tags.showplayers")[0];
	}

}