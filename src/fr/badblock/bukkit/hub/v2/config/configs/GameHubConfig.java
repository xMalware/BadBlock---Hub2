package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GameHubConfig extends HubConfig
{

	private boolean enabled;
	private String internalGameName;

	private Map<String, String> stats = new HashMap<>();

	public GameHubConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);

		enabled = getConfig().getBoolean("enabled");
		internalGameName = getConfig().getString("internalGameName");

		ConfigurationSection replace = getConfig().getConfigurationSection("replace");

		if (replace != null)
		{
			replace.getKeys(false).forEach(key ->
			{
				stats.put(key, replace.getString(key));
			});
		}
	}

}
