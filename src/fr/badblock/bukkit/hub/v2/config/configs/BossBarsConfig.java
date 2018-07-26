package fr.badblock.bukkit.hub.v2.config.configs;

import java.util.HashMap;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.bukkit.hub.v2.players.addons.BossBar;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.players.bossbars.BossBarColor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BossBarsConfig extends HubConfig
{

	private Map<String, BossBar>	bossBars = new HashMap<>();

	public BossBarsConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);
		getConfig().getKeys(false).forEach(string ->
		{

			String message = getConfig().getString(string + ".message");
			int time = getConfig().getInt(string + ".time");

			String rawColor = getConfig().getString(string + ".color");
			BossBarColor bossBarColor = get(rawColor);

			BossBar bossBar = new BossBar(message, time, bossBarColor);		
			bossBars.put(string, bossBar);

			System.out.println("[BadBlockHub / BossBars] Loaded " + string);
		});
	}

	private BossBarColor get(String name)
	{
		for (BossBarColor color : BossBarColor.values())
		{
			if (color.name().toUpperCase().equalsIgnoreCase(name.toUpperCase()))
			{
				return color;
			}
		}
		return BossBarColor.RED;
	}

}
