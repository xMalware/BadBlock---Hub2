package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.utils.tfa.AuthUtils;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagStateTFA extends HubTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object) 
	{
		return getTag(player);
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		String realName = player.getRealName() != null && !player.getRealName().isEmpty() ? player.getRealName() : player.getName();
		String key = AuthUtils.getAuthKey(realName);

		boolean enabled = key != null && !key.isEmpty();

		return enabled ? player.getTranslatedMessage("hub.tags.tfa_enabled")[0] : player.getTranslatedMessage("hub.tags.tfa_disabled")[0];
	}

}