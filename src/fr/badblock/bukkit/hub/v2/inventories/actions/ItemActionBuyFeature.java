package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
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
		// Get info
		HubPlayer hubPlayer = HubPlayer.get(player);
		HubStoredPlayer hubStoredPlayer = hubPlayer.getStoredPlayer();
		FeatureManager featureManager = FeatureManager.getInstance();
		// Get feature
		Feature feature = featuresConfig.getFeatures().get(featureRawName);
		// Already bought this feature
		if (featureManager.hasFeature(hubStoredPlayer, featureRawName))
		{
			player.sendTranslatedMessage("hub.buy.alreadybought", feature.getLevelNeeded(), player.getPlayerData().getLevel());
		}
		// Check needed level
		if (feature.getLevelNeeded() > player.getPlayerData().getLevel())
		{
			player.sendTranslatedMessage("hub.buy.notenoughlevels", feature.getLevelNeeded(), player.getPlayerData().getLevel());
			return;
		}
		// Check needed badcoins
		if (feature.getBadcoinsNeeded() > player.getPlayerData().getBadcoins())
		{
			player.sendTranslatedMessage("hub.buy.notenoughbadcoins", feature.getBadcoinsNeeded(), player.getPlayerData().getBadcoins());
			return;
		}
		// Check needed shop points
		if (feature.getShopPointsNeeded() > player.getShopPoints())
		{
			player.sendTranslatedMessage("hub.buy.notenoughshoppoints", feature.getShopPointsNeeded(), player.getShopPoints());
			return;
		}
		// Remove what's needed to
		int badcoinsToRemove = feature.getBadcoinsNeeded();
		int shopPointsToRemove = feature.getShopPointsNeeded();

		player.getPlayerData().removeBadcoins(badcoinsToRemove);
		player.removeShopPoints(shopPointsToRemove);
		
		// Add feature
		featureManager.addFeature(hubStoredPlayer, feature);
		// Save storage
		hubStoredPlayer.save(player);
		
		// Send bought message
		player.sendTranslatedMessage("hub.buy.bought", player.getTranslatedMessage("hub.features." + featureRawName + ".name")[0]);
	}

}
