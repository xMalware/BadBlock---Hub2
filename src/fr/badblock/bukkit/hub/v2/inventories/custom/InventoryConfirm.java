package fr.badblock.bukkit.hub.v2.inventories.custom;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryConfirm extends CustomInventory
{

	@Override
	public void work(BadblockPlayer player, ItemStack itemStack)
	{
		HubPlayer hubPlayer = HubPlayer.get(player);
		// Unknown buy feature
		if (hubPlayer.getBuyFeature() == null)
		{
			return;
		}
		// Unknown item stack
		if (itemStack == null)
		{
			return;
		}
		// Get feature raw
		String featureRawName = hubPlayer.getBuyFeature();
		// Close inventory
		player.closeInventory();
		// Cancel
		if (itemStack.getType().equals(Material.REDSTONE_BLOCK))
		{
			player.sendTranslatedMessage("buy.cancelled");
			return;
		}
		// Confirm
		if (itemStack.getType().equals(Material.EMERALD_BLOCK))
		{
			player.sendTranslatedMessage("buy.confirm");
			confirm(player, hubPlayer, featureRawName);
			return;		
		}
		player.sendTranslatedMessage("buy.unknownaction");
	}
	
	private void confirm(BadblockPlayer player, HubPlayer hubPlayer, String featureRawName)
	{
		HubStoredPlayer hubStoredPlayer = hubPlayer.getStoredPlayer();
		FeatureManager featureManager = FeatureManager.getInstance();
		FeaturesConfig featuresConfig = ConfigLoader.getFeatures();
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
