package fr.badblock.bukkit.hub.v2.inventories.tags.custom;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryTagLevelNeeded extends InventoryTag
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
		FeaturesConfig featuresConfig = ConfigLoader.getFeatures();
		// Get feature
		Feature feature = featuresConfig.getFeatures().get(featureRawName);
		// return
		return Integer.toString(feature.getLevelNeeded());
	}

	@Override
	public String getTag(BadblockPlayer player)
	{
		return null;
	}

}
