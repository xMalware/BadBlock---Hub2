package fr.badblock.bukkit.hub.v2.games.utils;

import fr.badblock.bukkit.hub.v2.games.utils.config.GameConfigManager;
import fr.badblock.gameapi.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */

public abstract class AbstractGameModule {

    private String configName;
    private String name;
    private YamlConfiguration config;

    public AbstractGameModule(String name, String configName) {
        this.name = name;
        this.configName = configName;
        this.config = GameConfigManager.getConfigByName(configName).getConfig();
    }

    public abstract void registerBukkitListener();
    public abstract void registerCommands();
    public abstract void loadConfig();

    protected YamlConfiguration getConfig(){
        return config;
    }

    protected YamlConfiguration getDefaultConfig(){
        return GameConfigManager.getConfigByName("config.yml").getConfig();
    }

    protected Location getDefaultLocation(){
        return ConfigUtils.convertStringToLocation(getDefaultConfig().getString(name));
    }
}
