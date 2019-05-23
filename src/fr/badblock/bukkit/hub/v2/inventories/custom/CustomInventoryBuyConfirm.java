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
import net.md_5.bungee.api.ChatColor;

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
		
		FeaturesConfig featuresConfig = ConfigLoader.getFeatures();
		
		Feature feature = featuresConfig.getFeatures().get(featureRawName);
		String displayFeatureName = ChatColor.translateAlternateColorCodes('&', feature.getName());
		
		// Cancel
		if (itemStack.getType().equals(Material.REDSTONE_BLOCK))
		{
			player.sendTranslatedMessage("hub.features.buy.cancel.cancelled", displayFeatureName);
			return true;
		}
		
		// Confirm
		if (itemStack.getType().equals(Material.EMERALD_BLOCK))
		{
			player.sendTranslatedMessage("hub.features.buy.confirm.confirmed", displayFeatureName);
			confirm(player, hubPlayer, featureRawName);
			return true;
		}
		
		player.sendTranslatedMessage("hub.features.buy.errors.unknownaction", displayFeatureName);
		return true;
	}

	public static void confirm(BadblockPlayer player, HubPlayer hubPlayer, String featureRawName)
	{
		if (!canBuy(player, hubPlayer, featureRawName))
		{
			return;
		}

		HubStoredPlayer hubStoredPlayer = hubPlayer.getStoredPlayer();
		FeatureManager featureManager = FeatureManager.getInstance();
		FeaturesConfig featuresConfig = ConfigLoader.getFeatures();
		
		Feature feature = featuresConfig.getFeatures().get(featureRawName);
		
		// Remove what's needed to
		int badcoinsToRemove = feature.getBadcoinsNeeded();
		int shopPointsToRemove = feature.getShopPointsNeeded();

		player.getPlayerData().removeBadcoins(badcoinsToRemove);
		player.removeShopPoints(shopPointsToRemove);

		// Add feature
		featureManager.addFeature(player, hubStoredPlayer, featureRawName);
		// Save storage
		hubStoredPlayer.save(player);
		
		String displayFeatureName = ChatColor.translateAlternateColorCodes('&', feature.getName());

		// Send bought message
		player.sendTranslatedMessage("hub.features.buy.bought", displayFeatureName);
	}

	public static boolean canBuy(BadblockPlayer player, HubPlayer hubPlayer, String featureRawName)
	{
		HubStoredPlayer hubStoredPlayer = hubPlayer.getStoredPlayer();
		FeatureManager featureManager = FeatureManager.getInstance();
		FeaturesConfig featuresConfig = ConfigLoader.getFeatures();
		// Get feature
		Feature feature = featuresConfig.getFeatures().get(featureRawName);
		
		String displayFeatureName = ChatColor.translateAlternateColorCodes('&', feature.getName());
		
		// Already bought this feature
		if (featureManager.hasFeature(player, hubStoredPlayer, featureRawName))
		{
			player.sendTranslatedMessage("hub.features.buy.errors.alreadybought", displayFeatureName, feature.getLevelNeeded(), player.getPlayerData().getLevel());
			return true;
		}
		
		// Check needed level
		if (feature.getLevelNeeded() > player.getPlayerData().getLevel())
		{
			player.sendTranslatedMessage("hub.features.buy.errors.notenoughlevels", displayFeatureName, feature.getLevelNeeded(), player.getPlayerData().getLevel());
			return false;
		}
		
		// Check needed badcoins
		if (feature.getBadcoinsNeeded() > player.getPlayerData().getBadcoins())
		{
			player.sendTranslatedMessage("hub.features.buy.errors.notenoughbadcoins", displayFeatureName, feature.getBadcoinsNeeded(), player.getPlayerData().getBadcoins());
			return false;
		}
		
		// Check needed shop points
		if (feature.getShopPointsNeeded() > player.getShopPoints())
		{
			player.sendTranslatedMessage("hub.features.buy.errors.notenoughshoppoints", displayFeatureName, feature.getShopPointsNeeded(), player.getShopPoints());
			return false;
		}
		
		return true;
	}

}