package fr.badblock.bukkit.hub.v2.games.course.events;

import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.course.task.CourseGameRunnable;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.selections.CuboidSelection;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

/**
 * Created by Toinetoine1 on 14/01/2019.
 */

public class CourseMove implements Listener {

    private CuboidSelection endCuboid;

    public CourseMove(Location pos1, Location pos2) {
        endCuboid = new CuboidSelection(pos1, pos2);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCuboidEnter(PlayerMoveEvent event) {
        if (CourseManager.getInstance().getWaitingPlayers().contains(event.getPlayer())) {
            if (!CourseManager.getInstance().getWinnersPlayersP().contains(event.getPlayer())) {
                if (endCuboid.isInSelection(event.getTo())) {
                    for (Player p : CourseManager.getInstance().getWaitingPlayers()) {
                        p.sendMessage(CourseManager.COURSE_PREFIX + "§e" + event.getPlayer().getName() + " §ba terminé la course en " + ( 240 - CourseGameRunnable.getTimeLeft() ) + " seconde(s)");
                    }
                    CourseManager.getInstance().getWinnersPlayersP().add((BadblockPlayer) event.getPlayer());

                    if (CourseManager.getInstance().getWinnersPlayersP().size() == CourseManager.getInstance().getWaitingPlayers().size()) {
                        CourseManager.getInstance().getWaitingPlayers().forEach(player -> {
                            player.sendMessage(CourseManager.COURSE_PREFIX + "§cTout le monde a fini le parcours ! Bravo !");
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
                        CourseManager.getInstance().getWinnersPlayersP().clear();
                        CourseManager.getInstance().getState().setState(GameState.WAITING);
                        CourseInteract.runnable.cancel();
                    }
                }
            }
        }
    }

}
