package fr.badblock.bukkit.hub.v2.games.course;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.course.events.CourseCommand;
import fr.badblock.bukkit.hub.v2.games.course.events.CourseInteract;
import fr.badblock.bukkit.hub.v2.games.course.events.CourseMove;
import fr.badblock.bukkit.hub.v2.games.course.events.CourseQuit;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.games.utils.AbstractGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.GameConfigManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */

public class CourseManager extends AbstractGameModule {

    @Getter
    private static CourseManager instance;

    public static final String COURSE_PREFIX = "§8[§6Course§8] ";
    public static final int MIN_PLAYER = 2;

    @Getter
    private List<Location> doorsToStart;
    @Getter
    private List<Location> waitingPos;
    @Getter
    private LinkedHashMap<Location, Boolean> doorsToEnter;
    @Getter
    @Setter
    private GameState state;

    @Getter
    private List<BadblockPlayer> waitingPlayers;
    @Getter
    private List<BadblockPlayer> winnersPlayersP;

    private Location cuboid_loc1;
    private Location cuboid_loc2;

    @Getter
    private Location teleportPoint;

    public CourseManager() {
        super("Course","course.yml");
        instance = this;
        state = GameState.WAITING;

        waitingPlayers = new ArrayList<>();
        waitingPos = new ArrayList<>();
        winnersPlayersP = new ArrayList<>();
        doorsToEnter = new LinkedHashMap<>();
        doorsToStart = new ArrayList<>();

    }

    @Override
    public void registerBukkitListener() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new CourseCommand(), BadBlockHub.getInstance());
        pm.registerEvents(new CourseInteract(), BadBlockHub.getInstance());
        pm.registerEvents(new CourseQuit(), BadBlockHub.getInstance());
        pm.registerEvents(new CourseMove(cuboid_loc1, cuboid_loc2), BadBlockHub.getInstance());
    }

    @Override
    public void registerCommands() {
    }

    @Override
    public void loadConfig() {

        for (String s : getConfig().getConfigurationSection("Enter_gate").getKeys(false)) {
            Location loc = new Location(
                    Bukkit.getWorld(getConfig().getString("Enter_gate." + s + ".world")),
                    getConfig().getInt("Enter_gate." + s + ".x"),
                    getConfig().getInt("Enter_gate." + s + ".y"),
                    getConfig().getInt("Enter_gate." + s + ".z"));
            doorsToEnter.put(loc, false);
        }

        for (String s : getConfig().getConfigurationSection("PlayerLoc").getKeys(false)) {
            Location loc = new Location(
                    Bukkit.getWorld(getConfig().getString("Enter_gate." + s + ".world")),
                    getConfig().getInt("PlayerLoc." + s + ".x"),
                    getConfig().getInt("PlayerLoc." + s + ".y"),
                    getConfig().getInt("PlayerLoc." + s + ".z"));
            waitingPos.add(loc);
        }

        for (String s : getConfig().getConfigurationSection("Start_gate").getKeys(false)) {
            Location loc = new Location(
                    Bukkit.getWorld(getConfig().getString("Start_gate." + s + ".world")),
                    getConfig().getInt("Start_gate." + s + ".x"),
                    getConfig().getInt("Start_gate." + s + ".y"),
                    getConfig().getInt("Start_gate." + s + ".z"));
            doorsToStart.add(loc);
        }

        cuboid_loc1 = new Location(
                Bukkit.getWorld(getConfig().getString("Cuboid.1.world")),
                getConfig().getInt("Cuboid.1.x"),
                getConfig().getInt("Cuboid.1.y"),
                getConfig().getInt("Cuboid.1.z"));

        cuboid_loc2 = new Location(
                Bukkit.getWorld(getConfig().getString("Cuboid.2.world")),
                getConfig().getInt("Cuboid.2.x"),
                getConfig().getInt("Cuboid.2.y"),
                getConfig().getInt("Cuboid.2.z"));


        teleportPoint = getDefaultLocation();

    }

}
