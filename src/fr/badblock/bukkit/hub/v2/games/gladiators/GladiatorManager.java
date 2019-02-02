package fr.badblock.bukkit.hub.v2.games.gladiators;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.gladiators.events.*;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import fr.badblock.bukkit.hub.v2.games.utils.CustomPlayerInventory;
import fr.badblock.bukkit.hub.v2.games.utils.AbstractGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.GameConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GladiatorManager extends AbstractGameModule {

    public static final String GLADIATOR_PREFIX = "§8[§6Gladiator§8] ";
    @Getter
    private static GladiatorManager instance;
    @Getter
    private HashMap<Player, CustomPlayerInventory> customInv;
    @Getter
    private Location teleportPoint;

    public GladiatorManager() {
        super("Gladiator", "gladiator.yml");
        instance = this;
        new MapManager();
        customInv = new HashMap<>();
    }

    @Override
    public void registerBukkitListener() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new GladiatorSign(), BadBlockHub.getInstance());
        pm.registerEvents(new GladiatorInteract(), BadBlockHub.getInstance());
        pm.registerEvents(new GladiatorDeath(), BadBlockHub.getInstance());
        pm.registerEvents(new GladiatorQuit(), BadBlockHub.getInstance());
        pm.registerEvents(new GladiatorCommand(), BadBlockHub.getInstance());
    }

    @Override
    public void registerCommands() {
    }

    @Override
    public void loadConfig() {

        for (String s : getConfig().getConfigurationSection("Maps").getKeys(false)) {
            List<Location> positions = new ArrayList<>();

            for (String position : getConfig().getConfigurationSection("Maps."+s+".Position").getKeys(false)) {
                Location loc = new Location(
                        Bukkit.getWorld(getConfig().getString("Maps." + s + ".Position."+position+".world")),
                        getConfig().getDouble("Maps." + s + ".Position."+position+".x"),
                        getConfig().getDouble("Maps." + s + ".Position."+position+".y"),
                        getConfig().getDouble("Maps." + s + ".Position."+position+".z"),
                        getConfig().getLong("Maps." + s + ".Position."+position+".yaw"),
                        getConfig().getLong("Maps." + s + ".Position."+position+".pitch"));
                positions.add(loc);
            }

            MapManager.get().getMaps().add(new Map(s, positions));
        }

        teleportPoint = getTeleportPoint();
    }
}
