package fr.badblock.bukkit.hub.v2.config.configs;

import fr.badblock.bukkit.hub.v2.config.HubConfig;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ShopConfig extends HubConfig
{

	private String			boosterInventoryCommand	= "";

	public ShopConfig(BadblockPlugin plugin, String fileName)
	{
		super(plugin, fileName);

		boosterInventoryCommand = getConfig().getString("commands.boosterinventory");
	}

}