package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureBuyConfirmInventory;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionBuyFeature extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		String featureRawName = actionData.toLowerCase();
		FeaturesConfig featuresConfig = ConfigLoader.getFeatures();
		// Unknown feature
		if (!featuresConfig.getFeatures().containsKey(featureRawName))
		{
			System.out.println("[BadBlockHub] Unknown feature '" + featureRawName + "'.");
			return;
		}
		// Buy
		FeatureBuyConfirmInventory.confirm(player, featureRawName);
	}

}
