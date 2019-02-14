package fr.badblock.bukkit.hub.v2.games.course.task;

import com.google.common.base.Joiner;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.HumanEntity;
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
            for (BadblockPlayer player : CourseManager.getInstance().getWaitingPlayers()) {
                if(!CourseManager.getInstance().getWinnersPlayersP().isEmpty())
                    player.sendMessage(CourseManager.COURSE_PREFIX + "§cLe temps est écoulé ! Bravo à §8" + Joiner.on(", ").join(CourseManager.getInstance().getWinnersPlayersP().stream().map(HumanEntity::getName).collect(Collectors.toList())) + " §c!");
                else
                    player.sendMessage(CourseManager.COURSE_PREFIX+"§cAucun joueur n'est allé au bout du Parcours dans le temps imparti..");

                //Don't replace this by performCommand (error async)
                player.chat("/spawn");
            }

            closeDoors();

            CourseManager.getInstance().getWaitingPlayers().clear();
            CourseManager.getInstance().getWaitingPlayers().clear();
            CourseManager.getInstance().getDoorsToEnter().values().forEach(b -> b = false);
            CourseManager.getInstance().setState(GameState.WAITING);
            cancel();
        } else {
            CourseManager.getInstance().getWaitingPlayers().forEach(player -> player.setLevel(timeLeft));
        }
        timeLeft--;
    }

    public static void closeDoors() {
        for(Location loc : CourseManager.getInstance().getDoorsToStart()){
            BlockState Gate1 = loc.getBlock().getState();
            Openable openable1 = (Openable) Gate1.getData();
            openable1.setOpen(false);
            Gate1.setData((MaterialData) openable1);
            Gate1.update();
        }
    }
}
