package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagHideParticles extends HubTag
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

		boolean hide = hubStoredPlayer.isHideParticles();
		return hide ? player.getTranslatedMessage("hub.tags.hideparticles")[0] : player.getTranslatedMessage("hub.tags.showparticles")[0];
	}

}
