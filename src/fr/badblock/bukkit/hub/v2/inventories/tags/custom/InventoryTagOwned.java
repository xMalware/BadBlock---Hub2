package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagOwned extends InventoryTag
{

	@Override
	public String getTag(BadblockPlayer player, InventoryItemObject object)
	{
		String featureRawName = null;
		for (InventoryAction inventoryAction : object.getActions())
		{
			if (inventoryAction.getAction().equals(CustomItemActionType.BUY_FEATURE))
			{
				featureRawName = inventoryAction.getActionData().toLowerCase();
			}
		}
		if (featureRawName == null)
		{
			return null;
		}
		boolean feature = FeatureManager.getInstance().hasFeature(player, HubStoredPlayer.get(player), featureRawName);
		return feature ? player.getTranslatedMessage("hub.features.tag_owned")[0] : player.getTranslatedMessage("hub.features.tag_notowned")[0];
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return null;
	}

}
