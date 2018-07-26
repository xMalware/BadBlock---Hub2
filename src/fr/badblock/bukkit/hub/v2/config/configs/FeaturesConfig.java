package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureNeeded;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureType;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class FeaturesConfig extends HubConfig
{

	private Map<String, Feature>	features = new HashMap<>();
	
	public FeaturesConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);
		getConfig().getKeys(false).forEach(string ->
		{
			String featurePath = string;
			String rawType = getConfig().getString(featurePath + ".type");
			
			FeatureType type = FeatureType.get(rawType);
			
			if (type == null)
			{
				System.out.println("[BadBlockHub / Feature] Unknown feature type : " + rawType);
				return;
			}
			
			String name = getConfig().getString(featurePath + ".name");
			int badcoinsNeeded = getConfig().getInt(featurePath + ".badcoinsNeeded");
			int shopPointsNeeded = getConfig().getInt(featurePath + ".shopPointsNeeded");
			int levelNeeded = getConfig().getInt(featurePath + ".levelNeeded");
			long expire = getConfig().getLong(featurePath + ".expire");
			
			// Feature needeed
			boolean buyable = getConfig().getBoolean(featurePath + ".needed.buyable");
			boolean everyoneHaveThis = getConfig().getBoolean(featurePath + ".needed.everyoneHaveThis");
			List<String> permissions = getConfig().getStringList(featurePath + ".needed.permissions");
			FeatureNeeded featureNeeded = new FeatureNeeded(buyable, everyoneHaveThis, permissions.stream().toArray(String[]::new));
			
			Feature feature = new Feature(type, name, badcoinsNeeded, shopPointsNeeded, levelNeeded, expire, featureNeeded);
			features.put(name, feature);
			System.out.println("[BadBlockHub / Features] Loaded " + name);
		});
	}
	
}
