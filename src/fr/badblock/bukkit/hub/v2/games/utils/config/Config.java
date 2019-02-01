package fr.badblock.bukkit.hub.v2.games.utils.config;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Toinetoine1 on 16/01/2019.
 */
public class Config {

    private File file;
    private String fileName;
    private YamlConfiguration config;

    public Config(String fileName) {

        File file = new File(BadBlockHub.getInstance().getDataFolder(), fileName);

        if (!file.exists()) {
            BadBlockHub.getInstance().saveResource(fileName, false);
        }

        this.file = file;
        this.fileName = fileName;
        this.config = YamlConfiguration.loadConfiguration(getFile());

    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

}
