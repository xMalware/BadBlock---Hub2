package fr.badblock.bukkit.hub.v2.games.spleef;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.spleef.events.*;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.games.utils.IGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getWorld;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */

public class SpleefManager implements IGameModule {

    public static final int MIN_PLAYER = 2;
    public static String SPLEEF_PREFIX = "§8[§6Spleef§8] ";

    @Getter
    private static SpleefManager instance;

    private Location cuboid_loc1;
    private Location cuboid_loc2;

    @Getter
    private Map<Player, SpleefPlayer> spleefPlayers;
    @Getter
    private GameState gameState;
    @Getter
    private Location spleefLoc;

    public SpleefManager() {
        instance = this;

        spleefPlayers = new HashMap<>();
        gameState = GameState.WAITING;
    }

    @Override
    public void registerBukkitListener() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new SpleefCommand(), BadBlockHub.getInstance());
        pm.registerEvents(new SpleefBreak(), BadBlockHub.getInstance());
        pm.registerEvents(new SpleefDamage(), BadBlockHub.getInstance());
        pm.registerEvents(new SpleefQuit(), BadBlockHub.getInstance());
        pm.registerEvents(new SpleefMove(cuboid_loc1, cuboid_loc2), BadBlockHub.getInstance());
    }

    @Override
    public void registerCommands() {
    }

    @Override
    public void loadConfig() {
        YamlConfiguration config = ConfigManager.getConfigByName("spleef.yml").getConfig();

        cuboid_loc1 = new Location(
                getWorld(config.getString("Cuboid.1.world")),
                config.getInt("Cuboid.1.x"),
                config.getInt("Cuboid.1.y"),
                config.getInt("Cuboid.1.z"));

        cuboid_loc2 = new Location(
                getWorld(config.getString("Cuboid.2.world")),
                config.getInt("Cuboid.2.x"),
                config.getInt("Cuboid.2.y"),
                config.getInt("Cuboid.2.z"));

        spleefLoc = new Location(
                Bukkit.getWorld(getConfig().getString("Spleef.world")),
                getConfig().getInt("Spleef.x"),
                getConfig().getInt("Spleef.y"),
                getConfig().getInt("Spleef.z"),
                getConfig().getInt("Spleef.yaw"),
                getConfig().getInt("Spleef.pitch"));
    }


    public FileConfiguration getConfig(){
        return BadBlockHub.getInstance().getConfig();
    }
}
