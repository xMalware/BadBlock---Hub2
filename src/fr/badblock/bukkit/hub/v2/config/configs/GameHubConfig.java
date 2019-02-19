package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.bukkit.hub.v2.npc.Hologram;
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
	private String rankedGameName;

	private Map<String, String> stats = new HashMap<>();
	private List<Hologram> holograms = new ArrayList<>();

	public GameHubConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);

		enabled = getConfig().getBoolean("enabled");
		rankedGameName = getConfig().getString("rankedGameName");
		internalGameName = getConfig().getString("internalGameName");

		ConfigurationSection replace = getConfig().getConfigurationSection("replace");

		if (replace != null)
		{
			replace.getKeys(false).forEach(key ->
			{
				stats.put(key, replace.getString(key));
			});
		}
		
		ConfigurationSection hologramSection = getConfig().getConfigurationSection("holograms");
		
		if (hologramSection != null)
		{
			Bukkit.getWorlds().forEach(world -> world.getEntitiesByClass(ArmorStand.class).forEach(e -> e.remove()));
			
			hologramSection.getKeys(false).forEach(key ->
			{
				holograms.add(new Hologram(hologramSection.getConfigurationSection(key)));
			});
		}
	}

}
