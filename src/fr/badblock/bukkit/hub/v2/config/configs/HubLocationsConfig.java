package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.utils.ConfigUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data@EqualsAndHashCode(callSuper = false) public class HubLocationsConfig extends HubConfig {

	private Map<String, Location>	locations = new HashMap<>();
	
	public HubLocationsConfig(BadblockPlugin plugin, String fileName) {
		super(plugin, fileName);
	}

	public Location getLocation(String name) {
		String key = "loc_" + name;
		if (locations.containsKey(key)) return locations.get(key);
		String data = getConfig().getString(name);
		Location location = ConfigUtils.convertStringToLocation(data);
		locations.put(key, location);
		return location;
	}
	
	public Location getBlockLocation(String name) {
		String key = "locBlock_" + name;
		if (locations.containsKey(key)) return locations.get(key);
		String data = getConfig().getString(name);
		Location location = ConfigUtils.convertStringToBlockLocation(data);
		locations.put(key, location);
		return location;
	}
	
}
