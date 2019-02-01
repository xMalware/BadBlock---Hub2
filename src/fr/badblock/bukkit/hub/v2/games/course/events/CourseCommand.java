package fr.badblock.bukkit.hub.v2.games.course.events;

import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Toinetoine1 on 14/01/2019.
 */
public class CourseCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (CourseManager.getInstance().getWaitingPlayers().contains(p)) {
            if (p.getGameMode() == GameMode.CREATIVE || p.hasPermission("lobbygames.bypass")) {
                return;
            }
            e.setCancelled(true);
            p.sendMessage(CourseManager.COURSE_PREFIX + "§cTu ne peux pas éxécuter de commandes en jeu !");
        }
    }

}
