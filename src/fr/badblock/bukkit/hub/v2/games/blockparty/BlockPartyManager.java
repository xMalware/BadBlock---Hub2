package fr.badblock.bukkit.hub.v2.games.blockparty;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.events.PartyInteract;
import fr.badblock.bukkit.hub.v2.games.blockparty.events.PartyMove;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.games.utils.IGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.ConfigManager;
import fr.badblock.gameapi.packets.out.play.PlayChangeGameState;
import fr.badblock.gameapi.utils.selections.CuboidSelection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;

public class BlockPartyManager implements IGameModule {

    public static final String BLOCK_PREFIX = "§8[§6BlockParty§8] ";
    public static final int MIN_PLAYER = 2;

    @Getter
    private static BlockPartyManager instance;

    @Getter
    private HashMap<Player, BlockPlayer> blockPlayers;
    @Getter
    private CuboidSelection cuboid;
    @Getter
    private Location gate;
    @Getter
    private Location teleport;
    @Getter
    private GameState gameState;

    public BlockPartyManager() {
        instance = this;
        blockPlayers = new HashMap<>();
        gameState = GameState.WAITING;
    }

    @Override
    public void registerBukkitListener() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PartyInteract(), BadBlockHub.getInstance());
        pm.registerEvents(new PartyMove(), BadBlockHub.getInstance());
    }

    @Override
    public void registerCommands() {

    }

    @Override
    public void loadConfig() {
        YamlConfiguration config = ConfigManager.getConfigByName("blockparty.yml").getConfig();

        cuboid = new CuboidSelection(
                new Location(Bukkit.getWorld(config.getString("Cuboid.Loc1.world")),
                        config.getInt("Cuboid.Loc1.x"),
                        config.getInt("Cuboid.Loc1.y"),
                        config.getInt("Cuboid.Loc1.z")),
                new Location(Bukkit.getWorld(config.getString("Cuboid.Loc2.world")),
                        config.getInt("Cuboid.Loc2.x"),
                        config.getInt("Cuboid.Loc2.y"),
                        config.getInt("Cuboid.Loc2.z"))
        );

        gate = new Location(Bukkit.getWorld(config.getString("Gate.world")),
                config.getInt("Gate.x"),
                config.getInt("Gate.y"),
                config.getInt("Gate.z"));

        teleport = new Location(Bukkit.getWorld(config.getString("Teleport.world")),
                config.getInt("Teleport.x"),
                config.getInt("Teleport.y"),
                config.getInt("Teleport.z"));
    }


}
