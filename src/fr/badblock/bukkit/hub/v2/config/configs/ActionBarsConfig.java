package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.bukkit.hub.v2.players.addons.ActionBar;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActionBarsConfig extends HubConfig
{

	private Map<String, ActionBar>	actionBars = new HashMap<>();
	
	public ActionBarsConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);
		getConfig().getKeys(false).forEach(string ->
		{
			
			String message = getConfig().getString(string + ".message");
			int time = getConfig().getInt(string + ".time");
			
			ActionBar actionBar = new ActionBar(message, time);
			actionBars.put(string, actionBar);
			
			System.out.println("[BadBlockHub / ActionBars] Loaded " + string);
		});
	}
	
}
