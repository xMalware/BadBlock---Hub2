package fr.badblock.bukkit.hub.v2.games.utils.config;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Toinetoine1 on 16/01/2019.
 */
public class GameConfigManager {

    private static List<GameConfig> gameConfigs = new ArrayList<>();

    public GameConfigManager() {
        if (!BadBlockHub.getInstance().getDataFolder().exists()) {
            BadBlockHub.getInstance().getDataFolder().mkdir();
        }

        gameConfigs.addAll(Arrays.asList(
                new GameConfig("course.yml"),
                new GameConfig("jump.yml"),
                new GameConfig("spleef.yml"),
                new GameConfig("shoot.yml"),
                new GameConfig("config.yml"),
                new GameConfig("gladiator.yml"),
                new GameConfig("blockparty.yml")
        ));
    }

    public static GameConfig getConfigByName(String name) {
        for (GameConfig gameConfig : gameConfigs) {
            if (name.equals(gameConfig.getFileName())) {
                return gameConfig;
            }
        }
        return null;
    }

    public static void refreshAllConfig() {
        gameConfigs.forEach(gameConfig -> gameConfig.setConfig(YamlConfiguration.loadConfiguration(gameConfig.getFile())));
    }

    public static List<GameConfig> getGameConfigs() {
        return gameConfigs;
    }

}
