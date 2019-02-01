package fr.badblock.bukkit.hub.v2.games.course.events;

import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Toinetoine1 on 14/01/2019.
 */

public class CourseQuit implements Listener {


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        CourseManager.getInstance().getWaitingPlayers().remove(player);
        CourseManager.getInstance().getWinnersPlayersP().remove(player);

        if (CourseManager.getInstance().getWaitingPlayers().size() == 0) {
            CourseManager.getInstance().getWinnersPlayersP().clear();
            CourseManager.getInstance().getState().setState(GameState.WAITING);

            if (CourseInteract.runnable != null)
                CourseInteract.runnable.cancel();
        }
    }

}
