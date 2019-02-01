package fr.badblock.bukkit.hub.v2.games.utils.config;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Toinetoine1 on 16/01/2019.
 */
public class ConfigManager {

    private static List<Config> configs = new ArrayList<>();

    public ConfigManager() {
        if (!BadBlockHub.getInstance().getDataFolder().exists()) {
            BadBlockHub.getInstance().getDataFolder().mkdir();
        }

        configs.addAll(Arrays.asList(
                new Config("course.yml"),
                new Config("jump.yml"),
                new Config("spleef.yml"),
                new Config("shoot.yml"),
                new Config("config.yml"),
                new Config("gladiator.yml"),
                new Config("blockparty.yml")
        ));
    }

    public static Config getConfigByName(String name) {
        for (Config config : configs) {
            if (name.equals(config.getFileName())) {
                return config;
            }
        }
        return null;
    }

    public static void refreshAllConfig() {
        configs.forEach(config -> config.setConfig(YamlConfiguration.loadConfiguration(config.getFile())));
    }

    public static List<Config> getConfigs() {
        return configs;
    }

}
