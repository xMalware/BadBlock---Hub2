package fr.badblock.bukkit.hub.v2.cosmetics.features;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import lombok.Setter;

public class FeatureManager
{

	@Getter @Setter
	private static FeatureManager	instance	= new FeatureManager();

	public void addFeature(BadblockPlayer player, HubStoredPlayer hubStoredPlayer, String featureRawName)
	{
		hubStoredPlayer.getFeatures().add(featureRawName);
		hubStoredPlayer.save(player);
	}

	public boolean hasFeature(Player player, HubStoredPlayer hubStoredPlayer, String featureRawName)
	{
		String[] splitter = featureRawName.split("_");
		
		if (splitter.length != 2)
		{	
			System.out.println("[BadBlockHub] A feature must have this pattern : type_name (" + featureRawName + ")");
			return false;
		}

		FeatureType featureType = FeatureType.get(splitter[0]);
		
		if (featureType == null)
		{
			System.out.println("[BadBlockHub] Unknown feature type for " + featureRawName);
			return false;
		}

		Feature feature = ConfigLoader.getFeatures().getFeatures().get(featureRawName);
		
		// Unknown feature
		if (feature == null)
		{
			return false;
		}

		FeatureNeeded featureNeeded = feature.getNeeded();
		
		if (featureNeeded == null)
		{
			return false;
		}

		if (featureNeeded.isEveryoneHaveThis())
		{
			return true;
		}

		if (featureNeeded.getPermissions() != null)
		{
			for (String permission : featureNeeded.getPermissions())
			{
				if (player.hasPermission(permission))
				{
					return true;
				}
			}
		}

		return hubStoredPlayer.getFeatures().contains(featureRawName);
	}
	
	public void generate(String rawName)
	{
		FeaturesConfig config = ConfigLoader.getFeatures();
		if (config.getFeatures().containsKey(rawName))
		{
			return;
		}
		
		System.out.println("[BadBlockHub] Generating " + rawName);
		config.getConfig().set(rawName + ".name", rawName);
		config.getConfig().set(rawName + ".type", FeatureType.get(rawName.split("_")[0]).name());
		config.getConfig().set(rawName + ".badcoinsNeeded", 0);
		config.getConfig().set(rawName + ".shopPointsNeeded", 0);
		config.getConfig().set(rawName + ".levelNeeded", 0);
		config.getConfig().set(rawName + ".expire", -1);
		// Feature needeed
		config.getConfig().set(rawName + ".needed.buyable", false);
		config.getConfig().set(rawName + ".needed.everyoneHaveThis", false);
		config.getConfig().set(rawName + ".needed.permissions", new ArrayList<>());
	
		try
		{
			config.getConfig().save(config.getFile());
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}

}
