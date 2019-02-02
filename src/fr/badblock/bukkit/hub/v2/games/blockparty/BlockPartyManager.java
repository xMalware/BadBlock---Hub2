package fr.badblock.bukkit.hub.v2.games.blockparty;

import java.util.HashMap;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.events.PartyInteract;
import fr.badblock.bukkit.hub.v2.games.blockparty.events.PartyMove;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.games.utils.AbstractGameModule;
import fr.badblock.gameapi.utils.selections.CuboidSelection;
import lombok.Getter;

public class BlockPartyManager extends AbstractGameModule {

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
    private Location teleportPoint;
    @Getter
    @Setter
    private GameState gameState;

    public BlockPartyManager() {
        super("BlockParty","blockparty.yml");
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

        cuboid = new CuboidSelection(
                new Location(Bukkit.getWorld(getConfig().getString("Cuboid.Loc1.world")),
                        getConfig().getInt("Cuboid.Loc1.x"),
                        getConfig().getInt("Cuboid.Loc1.y"),
                        getConfig().getInt("Cuboid.Loc1.z")),
                new Location(Bukkit.getWorld(getConfig().getString("Cuboid.Loc2.world")),
                        getConfig().getInt("Cuboid.Loc2.x"),
                        getConfig().getInt("Cuboid.Loc2.y"),
                        getConfig().getInt("Cuboid.Loc2.z"))
        );

        gate = new Location(Bukkit.getWorld(getConfig().getString("Gate.world")),
                getConfig().getInt("Gate.x"),
                getConfig().getInt("Gate.y"),
                getConfig().getInt("Gate.z"));

        teleport = new Location(Bukkit.getWorld(getConfig().getString("Teleport.world")),
                getConfig().getInt("Teleport.x"),
                getConfig().getInt("Teleport.y"),
                getConfig().getInt("Teleport.z"));

        teleportPoint = getDefaultLocation();
    }


}
