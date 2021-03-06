package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.api.common.utils.i18n.ChatColor;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureWorker;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionUseFeature extends CustomItemAction
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
	
		player.closeInventory();
		
		// Get info
		HubPlayer hubPlayer = HubPlayer.get(player);
		HubStoredPlayer hubStoredPlayer = hubPlayer.getStoredPlayer();
		
		// Get feature
		Feature feature = featuresConfig.getFeatures().get(featureRawName);

		String displayFeatureName = ChatColor.translateAlternateColorCodes('&', feature.getName());
		
		// Check needed level
		if (!FeatureManager.getInstance().hasFeature(player, hubStoredPlayer, featureRawName))
		{
			player.sendTranslatedMessage("hub.features.notowned", displayFeatureName);
			return;
		}
		
		// Send use message
		player.sendTranslatedMessage("hub.features.use", displayFeatureName);
		
		// Do what we should
		FeatureWorker.work(player, feature, featureRawName);
	}

}
