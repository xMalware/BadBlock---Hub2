package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.utils.ConfigUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class JumpLocationsConfig extends HubConfig
{

	private Map<Integer, Location>	checkpoints;
	private Location							    start;
	private Location							    end;
	
	public JumpLocationsConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);
		
		ConfigurationSection checkpoints = getConfig().getConfigurationSection("checkpoints");
		for (String k : checkpoints.getKeys(false))
		{
			int i = 0;
			try
			{
				i = Integer.parseInt(k);
			}
			catch (Exception error)
			{
				
			}
			
			String rawLoc = checkpoints.getString(k);
			Location loc = ConfigUtils.convertStringToLocation(rawLoc);
			this.getCheckpoints().put(i, loc);
		}

		start = ConfigUtils.convertStringToLocation(getConfig().getString("start"));
		end = ConfigUtils.convertStringToLocation(getConfig().getString("end"));
	}
	
}
