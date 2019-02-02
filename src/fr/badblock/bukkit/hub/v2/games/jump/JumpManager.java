package fr.badblock.bukkit.hub.v2.games.jump;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.jump.events.JumpMove;
import fr.badblock.bukkit.hub.v2.games.jump.objects.JumpPlayer;
import fr.badblock.bukkit.hub.v2.games.utils.IGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */
public class JumpManager implements IGameModule {

    @Getter
    private static JumpManager instance;
    public static final String JUMP_PREFIX = "§8[§6Jump§8] ";
    @Getter
    private Map<Player, JumpPlayer> jumpPlayers;
    @Getter
    private List<Location> checkpoint;

    public JumpManager() {
        instance = this;
        jumpPlayers = new HashMap<>();
        checkpoint = new ArrayList<>();
    }

    @Override
    public void registerBukkitListener() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new JumpMove(), BadBlockHub.getInstance());
    }

    @Override
    public void registerCommands() {

    }

    @Override
    public void loadConfig() {
        YamlConfiguration config = ConfigManager.getConfigByName("jump.yml").getConfig();
        for (String s : config.getConfigurationSection("Location").getKeys(false)) {
            Location loc = new Location(
                    Bukkit.getWorld(config.getString("Location." + s + ".world")),
                    config.getInt("Location." + s + ".x"),
                    config.getInt("Location." + s + ".y"),
                    config.getInt("Location." + s + ".z"));
            checkpoint.add(loc);
        }
    }
}
