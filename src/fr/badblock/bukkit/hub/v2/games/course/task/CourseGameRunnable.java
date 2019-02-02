package fr.badblock.bukkit.hub.v2.games.course.task;

import com.google.common.base.Joiner;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

/**
 * Created by Toinetoine1 on 14/01/2019.
 */

public class CourseGameRunnable extends BukkitRunnable {

    @Getter
    private static int timeLeft = 240;

    public CourseGameRunnable() {
        timeLeft = 240;
    }

    @Override
    public void run() {
        if(timeLeft <= 0){
            CourseManager.getInstance().getWaitingPlayers().forEach(player -> {
                player.sendMessage(CourseManager.COURSE_PREFIX + "§cLe temps est écoulé ! Bravo à §8"+ Joiner.on(", ").join(CourseManager.getInstance().getWinnersPlayersP().stream().map(badblockPlayer -> badblockPlayer.getName()).collect(Collectors.toList()))+" §c!");
                player.performCommand("spawn");
            });

            for(Location loc : CourseManager.getInstance().getDoorsToStart()){
                BlockState Gate1 = loc.getBlock().getState();
                Openable openable1 = (Openable) Gate1.getData();
                openable1.setOpen(false);
                Gate1.setData((MaterialData) openable1);
                Gate1.update();
            }

            CourseManager.getInstance().getWaitingPlayers().clear();
            CourseManager.getInstance().getWaitingPlayers().clear();
            CourseManager.getInstance().setState(GameState.WAITING);
            cancel();
        } else {
            CourseManager.getInstance().getWaitingPlayers().forEach(player -> player.setExp(timeLeft));
        }
        timeLeft--;
    }
}
