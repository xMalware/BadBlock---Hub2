package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureType;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeaturesConfig extends HubConfig
{

	private Map<String, Feature>	features = new HashMap<>();
	
	public FeaturesConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);
		getConfig().getKeys(false).forEach(string ->
		{
			String featurePath = getConfig().getString(string);
			String rawType = getConfig().getString(featurePath + ".type");
			FeatureType type = FeatureType.get(rawType);
			if (type == null)
			{
				System.out.println("[BadBlockHub] Unknown feature type : " + rawType);
			}
			String name = getConfig().getString(featurePath + ".name");
			int badcoinsNeeded = getConfig().getInt(featurePath + ".badcoinsNeeded");
			int shopPointsNeeded = getConfig().getInt(featurePath + ".shopPointsNeeded");
			int levelNeeded = getConfig().getInt(featurePath + ".levelNeeded");
			long expire = getConfig().getLong(featurePath + ".expire");
			Feature feature = new Feature(type, name, badcoinsNeeded, shopPointsNeeded, levelNeeded, expire);
			String rawName = (type.name() + "_" + name).toLowerCase();
			features.put(rawName, feature);
			System.out.println("[BadBlockHub] Loaded feature: " + rawName);
		});
	}
	
}
