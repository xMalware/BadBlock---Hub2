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
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if (CourseManager.getInstance().getWaitingPlayers().contains(player)) {
            if (CourseManager.getInstance().getState().equals(GameState.STARTING) || CourseManager.getInstance().getState().equals(GameState.WAITING)) {
                if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                    player.teleport(event.getFrom());
                }
            } else if (!CourseManager.getInstance().getWinnersPlayersP().contains(player)) {
                if (endCuboid.isInSelection(event.getTo())) {
                    for (Player p : CourseManager.getInstance().getWaitingPlayers()) {
                        p.sendMessage(CourseManager.COURSE_PREFIX + "§e" + player.getName() + " §ba terminé la course en " + (240 - CourseGameRunnable.getTimeLeft()) + " seconde(s)");
                    }
                    CourseManager.getInstance().getWinnersPlayersP().add(player);

                    if (CourseManager.getInstance().getWinnersPlayersP().size() == CourseManager.getInstance().getWaitingPlayers().size()) {
                        CourseManager.getInstance().getWaitingPlayers().forEach(player1 -> {
                            player1.sendMessage(CourseManager.COURSE_PREFIX + "§cTout le monde a fini le parcours ! Bravo !");
                            player1.performCommand("spawn");
                        });

                        closeDoors();

                        CourseManager.getInstance().getWaitingPlayers().clear();
                        CourseManager.getInstance().getWinnersPlayersP().clear();
                        CourseManager.getInstance().setState(GameState.WAITING);
                        CourseInteract.runnable.cancel();
                    }
                }
            }
        }
    }

    static void closeDoors() {
        CourseGameRunnable.closeDoors();
    }

}
