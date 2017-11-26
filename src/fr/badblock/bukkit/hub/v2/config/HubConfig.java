package fr.badblock.bukkit.hub.v2.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.badblock.gameapi.BadblockPlugin;
import lombok.Data;

@Data public class HubConfig {
	
	private YamlConfiguration 		config;
	
	public HubConfig(BadblockPlugin plugin, String fileName) {
		File file = new File(plugin.getDataFolder(), fileName + ".yml");
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		config = new YamlConfiguration();
		try {
			config.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
