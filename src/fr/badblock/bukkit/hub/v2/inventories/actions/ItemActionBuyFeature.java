package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureBuyConfirmInventory;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureNeeded;
import fr.badblock.bukkit.hub.v2.inventories.custom.CustomInventoryBuyConfirm;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
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
		
		Feature feature = featuresConfig.getFeatures().get(featureRawName);
		// Null feature?
		if (feature == null)
		{
			return;
		}
		
		HubStoredPlayer hubStoredPlayer = HubStoredPlayer.get(player);
		if (FeatureManager.getInstance().hasFeature(player, hubStoredPlayer, featureRawName))
		{
			CustomItemActionType.USE_FEATURE.getAction().execute(player, CustomItemActionType.USE_FEATURE, actionData);
			return;
		}
		
		// Feature needed
		FeatureNeeded featureNeeded = feature.getNeeded();
		if (feature.getNeeded() == null)
		{
			return;
		}
		
		if (!featureNeeded.isBuyable())
		{
			player.sendTranslatedMessage("hub.features.buy.errors.notbuyable");
			return;
		}
		
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer == null)
		{
			return;
		}
		
		if (!CustomInventoryBuyConfirm.canBuy(player, hubPlayer, featureRawName))
		{
			return;
		}
		
		// Buy
		FeatureBuyConfirmInventory.confirm(player, featureRawName);
	}

}
