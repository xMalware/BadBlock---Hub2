package fr.badblock.bukkit.hub.v2.games.jump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.jump.events.JumpMove;
import fr.badblock.bukkit.hub.v2.games.jump.objects.JumpPlayer;
import fr.badblock.bukkit.hub.v2.games.utils.AbstractGameModule;
import lombok.Getter;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */
public class JumpManager extends AbstractGameModule {

    @Getter
    private static JumpManager instance;
    public static final String JUMP_PREFIX = "§8[§6Jump§8] ";
    @Getter
    private Map<Player, JumpPlayer> jumpPlayers;
    @Getter
    private List<Location> checkpoint;
    @Getter
    private Location teleportPoint;

    public JumpManager() {
        super("Jump","jump.yml");
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

        for (String s : getConfig().getConfigurationSection("Location").getKeys(false)) {
            Location loc = new Location(
                    Bukkit.getWorld(getConfig().getString("Location." + s + ".world")),
                    getConfig().getInt("Location." + s + ".x"),
                    getConfig().getInt("Location." + s + ".y"),
                    getConfig().getInt("Location." + s + ".z"));
            checkpoint.add(loc);
        }

        teleportPoint = getDefaultLocation();
    }
}
