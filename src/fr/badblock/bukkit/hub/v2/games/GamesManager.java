package fr.badblock.bukkit.hub.v2.games;

import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.utils.AbstractGameModule;
import fr.badblock.bukkit.hub.v2.games.utils.config.GameConfigManager;
import fr.badblock.gameapi.BadblockPlugin;

import java.util.ArrayList;
import java.util.List;

public class GamesManager {

    private static List<AbstractGameModule> modules = new ArrayList<>();

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
    }
}
