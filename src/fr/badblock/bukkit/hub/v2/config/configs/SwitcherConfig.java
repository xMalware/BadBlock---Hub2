package fr.badblock.bukkit.hub.v2.config.configs;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SwitcherConfig extends HubConfig
{

	private boolean		gameEnabled;
	private boolean		npcSyncEnabled;
	
	public SwitcherConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);

		gameEnabled = getConfig().getBoolean("gameEnabled", false);
		npcSyncEnabled = getConfig().getBoolean("npcSync", false);
	}
	
}
