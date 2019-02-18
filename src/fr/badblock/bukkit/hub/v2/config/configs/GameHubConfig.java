package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

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

		getConfig().getKeys(false).forEach(key ->
		{
			stats.put(key, getConfig().getString(key));
		});
	}

}
