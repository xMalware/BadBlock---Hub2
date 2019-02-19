package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.bukkit.hub.v2.players.addons.TempTitle;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class JoinTitleConfig extends HubConfig
{

	private Map<String, TempTitle>	titles = new HashMap<>();
	
	public JoinTitleConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);
		getConfig().getKeys(false).forEach(string ->
		{

			String title = getConfig().getString(string + ".title");
			String subtitle = getConfig().getString(string + ".subtitle");
			int time = getConfig().getInt(string + ".time");
			
			TempTitle t = new TempTitle(title, subtitle, time);
			titles.put(string, t);
			
			System.out.println("[BadBlockHub / Titles] Loaded " + string);
		});
	}
	
}
