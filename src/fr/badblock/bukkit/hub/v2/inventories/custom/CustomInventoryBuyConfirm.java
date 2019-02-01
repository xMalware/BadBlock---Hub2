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
import lombok.Getter;

public class CustomInventoryBuyConfirm extends CustomInventory
{

	@Getter static CustomInventoryBuyConfirm instance = new CustomInventoryBuyConfirm();
	
	@Override
	public boolean work(BadblockPlayer player, ItemStack itemStack)
	{
		HubPlayer hubPlayer = HubPlayer.get(player);
		// Unknown buy feature
		if (hubPlayer.getBuyFeature() == null)
		{
			return false;
		}
		// Unknown item stack
		if (itemStack == null)
		{
			return false;
		}
		// Get feature raw
		String featureRawName = hubPlayer.getBuyFeature();
		// Close inventory
		player.closeInventory();
		// Remove temp buy feature
		hubPlayer.setBuyFeature(null);
		// Remove custom inventory
		hubPlayer.setCustomInventory(null);
		// Cancel
		if (itemStack.getType().equals(Material.REDSTONE_BLOCK))
		{
			player.sendTranslatedMessage("hub.features.buy.cancel.cancelled", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0]);
			return true;
		}
		// Confirm
		if (itemStack.getType().equals(Material.EMERALD_BLOCK))
		{
			player.sendTranslatedMessage("hub.features.buy.confirm.confirmed", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0]);
			confirm(player, hubPlayer, featureRawName);
			return true;
		}
		player.sendTranslatedMessage("hub.features.buy.errors.unknownaction", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0]);
		return true;
	}
	
	private void confirm(BadblockPlayer player, HubPlayer hubPlayer, String featureRawName)
	{
		HubStoredPlayer hubStoredPlayer = hubPlayer.getStoredPlayer();
		FeatureManager featureManager = FeatureManager.getInstance();
		FeaturesConfig featuresConfig = ConfigLoader.getFeatures();
		// Get feature
		Feature feature = featuresConfig.getFeatures().get(featureRawName);
		// Already bought this feature
		if (featureManager.hasFeature(player, hubStoredPlayer, featureRawName))
		{
			player.sendTranslatedMessage("hub.features.buy.errors.alreadybought", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0], feature.getLevelNeeded(), player.getPlayerData().getLevel());
		}
		// Check needed level
		if (feature.getLevelNeeded() > player.getPlayerData().getLevel())
		{
			player.sendTranslatedMessage("hub.features.buy.errors.notenoughlevels", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0], feature.getLevelNeeded(), player.getPlayerData().getLevel());
			return;
		}
		// Check needed badcoins
		if (feature.getBadcoinsNeeded() > player.getPlayerData().getBadcoins())
		{
			player.sendTranslatedMessage("hub.features.buy.errors.notenoughbadcoins", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0], feature.getBadcoinsNeeded(), player.getPlayerData().getBadcoins());
			return;
		}
		// Check needed shop points
		if (feature.getShopPointsNeeded() > player.getShopPoints())
		{
			player.sendTranslatedMessage("hub.features.buy.errors.notenoughshoppoints", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0], feature.getShopPointsNeeded(), player.getShopPoints());
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
		player.sendTranslatedMessage("hub.features.buy.bought", player.getTranslatedMessage("hub.features.names." + featureRawName.replace("_", "."))[0]);
	}

}