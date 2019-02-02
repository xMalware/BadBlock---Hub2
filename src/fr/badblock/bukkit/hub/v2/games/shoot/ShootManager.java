package fr.badblock.bukkit.hub.v2.games.shoot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.shoot.events.ShootBlock;
import fr.badblock.bukkit.hub.v2.games.shoot.events.ShootInteract;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.Box;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.ShootPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.games.utils.IGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.ConfigManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

/**
 * Created by Toinetoine1 on 18/01/2019.
 */
public class ShootManager implements IGameModule {

    public static final String SHOOT_PREFIX = "§8[§6Tir à l'arc§8] ";
    public static final int MIN_PLAYER = 1;
    @Getter
    private static ShootManager instance;

    @Getter
    private Map<BadblockPlayer, ShootPlayer> shootPlayers;
    @Getter
    private List<Box> boxes;
    @Getter
    private List<Location> particleLoc;
    @Getter
    private ArmorStand armorStand;
    @Getter
    @Setter
    private GameState gameState;

    @Getter
    private Location shootLoc;

    public ShootManager() {
        instance = this;

        shootPlayers = new HashMap<>();
        boxes = new ArrayList<>();
        particleLoc = new ArrayList<>();
        gameState = GameState.WAITING;
    }

    @Override
    public void registerBukkitListener() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new ShootInteract(), BadBlockHub.getInstance());
        pm.registerEvents(new ShootBlock(), BadBlockHub.getInstance());
    }

    @Override
    public void registerCommands() {
    }

    @Override
    public void loadConfig() {
        YamlConfiguration config = ConfigManager.getConfigByName("shoot.yml").getConfig();

        for (String s : config.getConfigurationSection("Particule").getKeys(false)) {
            Location particleLoc = new Location(
                    Bukkit.getWorld(config.getString("Particule." + s + ".world")),
                    config.getInt("Particule." + s + ".x"),
                    config.getInt("Particule." + s + ".y"),
                    config.getInt("Particule." + s + ".z"));

            Location cuboidLoc1 = new Location(
                    Bukkit.getWorld(config.getString("Cuboid." + s + ".Loc1.world")),
                    config.getInt("Cuboid." + s + ".Loc1.x"),
                    config.getInt("Cuboid." + s + ".Loc1.y"),
                    config.getInt("Cuboid." + s + ".Loc1.z"));

            Location cuboidLoc2 = new Location(
                    Bukkit.getWorld(config.getString("Cuboid." + s + ".Loc2.world")),
                    config.getInt("Cuboid." + s + ".Loc2.x"),
                    config.getInt("Cuboid." + s + ".Loc2.y"),
                    config.getInt("Cuboid." + s + ".Loc2.z"));

            this.particleLoc.add(particleLoc);
            this.boxes.add(new Box(particleLoc, cuboidLoc1, cuboidLoc2));
        }

        Location loc_armorStand = new Location(
                Bukkit.getWorld(config.getString("Armor_stand.world")),
                config.getInt("Armor_stand.x"),
                config.getInt("Armor_stand.y"),
                config.getInt("Armor_stand.z"),
                config.getLong("Armor_stand.yaw"),
                config.getLong("Armor_stand.pitch"));
        armorStand = (ArmorStand) loc_armorStand.getWorld().spawnEntity(loc_armorStand, EntityType.ARMOR_STAND);
        armorStand.setCustomName("Odanorr");
        armorStand.setArms(true);
        armorStand.setVisible(true);
        armorStand.setCustomNameVisible(true);

        shootLoc = new Location(
                Bukkit.getWorld(getConfig().getString("TirArc.world")),
                getConfig().getInt("TirArc.x"),
                getConfig().getInt("TirArc.y"),
                getConfig().getInt("TirArc.z"),
                getConfig().getInt("TirArc.yaw"),
                getConfig().getInt("TirArc.pitch"));
    }


    public FileConfiguration getConfig(){
        return BadBlockHub.getInstance().getConfig();
    }


}
