package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureType;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class HatConfig extends HubConfig
{

	private Map<String, String>	hats = new HashMap<>();

	public HatConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);
		getConfig().getKeys(false).forEach(key ->
		{
			key = key.toLowerCase();

			String skinOwner = getConfig().getString(key);
			
			String rawName = FeatureType.HATS.name().toLowerCase() + "_" + key;
			FeatureManager.getInstance().generate(rawName);
			
			CustomHats.add(key, skinOwner);
			System.out.println("[BadBlockHub / H] Loaded " + key);
		});
	}

}