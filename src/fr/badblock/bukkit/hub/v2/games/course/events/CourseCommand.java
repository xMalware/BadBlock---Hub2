package fr.badblock.bukkit.hub.v2.games.course.events;

import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Toinetoine1 on 14/01/2019.
 */
public class CourseCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if(CourseManager.getInstance().getWaitingPlayers().contains(player)){
            ArrayList<String> cmds = new ArrayList<>(Arrays.asList("/hub","/lobby","/spawn"));
            if(cmds.contains(event.getMessage())){
                CourseManager.getInstance().getWaitingPlayers().remove(player);
                CourseManager.getInstance().getWinnersPlayersP().remove(player);
                player.sendMessage(CourseManager.COURSE_PREFIX + "§cTu viens de quitter la partie !");
            }
        }
    }

}
