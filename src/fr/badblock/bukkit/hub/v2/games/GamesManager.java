package fr.badblock.bukkit.hub.v2.games;

import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.utils.AbstractGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.GameConfigManager;
import fr.badblock.bukkit.hub.v2.utils.FeatureUtils;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class GamesManager implements Listener {

    private static List<AbstractGameModule> modules = new ArrayList<>();
    public static final int TIME_TO_START = 30;

    public static void load(BadblockPlugin plugin) {
        new GameConfigManager();

        modules.add(new CourseManager());
        modules.add(new JumpManager());
        modules.add(new SpleefManager());
        modules.add(new ShootManager());
        modules.add(new GladiatorManager());
        modules.add(new BlockPartyManager());

        modules.forEach(module -> {
            //On préfereras load les configs en premier car ne demande aucune dépendance
            module.loadConfig();

            module.registerBukkitListener();
            module.registerCommands();
        });

        plugin.getServer().getPluginManager().registerEvents(new GamesManager(), plugin);
    }

    @EventHandler
    public void onFlyExecute(PlayerCommandPreprocessEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        String message = event.getMessage();

        if(FeatureUtils.isInAGame(player) && message.contains("/fly")){
            event.setCancelled(true);
        }
    }
}
