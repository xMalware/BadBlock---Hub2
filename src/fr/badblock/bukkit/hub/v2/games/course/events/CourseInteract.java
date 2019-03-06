package fr.badblock.bukkit.hub.v2.games.course.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.badblock.bukkit.hub.v2.games.GamesManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.course.task.CourseGameRunnable;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.utils.FeatureUtils;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */

public class CourseInteract implements Listener {

    static CourseGameRunnable runnable;
    private int i;

    public CourseInteract() {
        i = GamesManager.TIME_TO_START;
    }

	@EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (block != null && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.SPRUCE_FENCE_GATE) {
            if (CourseManager.getInstance().getDoorsToEnter().keySet().stream().anyMatch(location -> block.getLocation().equals(location))) {
                event.setCancelled(true);

                if (GameState.INGAME.equals(CourseManager.getInstance().getState())) {
                    player.sendMessage(CourseManager.COURSE_PREFIX + "§cUne partie est déjà en cours. Veuillez attendre qu'elle se termine..");
                    return;
                }

                if(JumpManager.getInstance().getJumpPlayers().containsKey(player)){
                	JumpManager.getInstance().getJumpPlayers().remove(player);
                    player.sendMessage(JumpManager.JUMP_PREFIX + "§cVous avez quitté le jump.");
                    return;
                }

                List<BadblockPlayer> waitingPlayers = CourseManager.getInstance().getWaitingPlayers();

                if (!waitingPlayers.contains(player)) {
                    if(CourseManager.getInstance().getDoorsToEnter().get(block.getLocation())){
                        player.sendMessage("§cUn joueur est déjà présent dans cet endroit");
                        return;
                    }

                    waitingPlayers.add(player);
                    player.sendMessage(CourseManager.COURSE_PREFIX + "§bTu as rejoint la partie.");
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    FeatureUtils.removeAllFeatures(player);

                    int pos = 0;
                    for(int i = 0; i < CourseManager.getInstance().getDoorsToEnter().entrySet().size(); i++){
                        if(CourseManager.getInstance().getDoorsToEnter().keySet().toArray()[i].equals(block.getLocation())){
                            pos = i;
                            break;
                        }
                    }

                    player.teleport(CourseManager.getInstance().getWaitingPos().get(pos));

                    if (waitingPlayers.size() >= CourseManager.MIN_PLAYER) {
                        player.sendMessage(CourseManager.COURSE_PREFIX + "§cLa partie va commencer ! Patientez "+i+"sec...");

                        CourseMove.closeDoors();

                        if(!GameState.STARTING.equals(CourseManager.getInstance().getState())){
                            i = GamesManager.TIME_TO_START;
                            CourseManager.getInstance().setState(GameState.STARTING);

                            new BukkitRunnable() {

                                List<Integer> timeToTick = new ArrayList<>(Arrays.asList(60, 30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1));

                                @Override
                                public void run() {
                                    if (waitingPlayers.size() < CourseManager.MIN_PLAYER) {
                                        waitingPlayers.forEach(player -> {
                                            player.sendMessage(CourseManager.COURSE_PREFIX + "§cNombre de joueur insuffisant !");
                                            player.sendTitle("§cNombre de joueur insuffisant !", "§9Annulation...");
                                        });
                                        CourseManager.getInstance().setState(GameState.WAITING);
                                        cancel();
                                    }

                                    waitingPlayers.forEach(p -> {
                                        if (i != 0) {
                                            p.setLevel(i);
                                            if(timeToTick.contains(i)){
                                                p.sendTitle("", "§c" + i);
                                                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                                                p.sendMessage(CourseManager.COURSE_PREFIX+ "La partie commence dans §c" + i+ " §8secondes");
                                            }
                                        } else {
                                            if (waitingPlayers.size() < CourseManager.MIN_PLAYER) {
                                                waitingPlayers.forEach(player -> player.sendMessage(CourseManager.COURSE_PREFIX + "§cNombre de joueur insuffisant !"));
                                                CourseManager.getInstance().setState(GameState.WAITING);
                                                cancel();
                                                return;
                                            }

                                            p.sendMessage(CourseManager.COURSE_PREFIX+"La partie commence !");
                                            p.sendTitle("", "");
                                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                            CourseManager.getInstance().setState(GameState.INGAME);

                                            for (Location loc : CourseManager.getInstance().getDoorsToStart()) {
                                                BlockState Gate1 = loc.getBlock().getState();
                                                Openable openable1 = (Openable) Gate1.getData();
                                                openable1.setOpen(true);
                                                Gate1.setData((MaterialData) openable1);
                                                Gate1.update();
                                            }

                                            cancel();
                                        }

                                    });
                                    if(i == 0){
                                        runnable = new CourseGameRunnable();
                                        runnable.runTaskTimerAsynchronously(BadBlockHub.getInstance(), 0, 20);
                                    }

                                    if (i <= 0){
                                        i = GamesManager.TIME_TO_START;
                                        cancel();
                                        return;
                                    }

                                    i--;
                                }

                            }.runTaskTimer(BadBlockHub.getInstance(), 0, 20);
                        }
                    } else {
                        String key = "Course";

                        if(GlobalFlags.has(key))
                            return;

                        GlobalFlags.set(key, 60000);

                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.sendMessage("§5§m------------------------------");
                            p.sendMessage(CourseManager.COURSE_PREFIX + "§3Une course va bientôt commencer !");
                            TextComponent tc = new TextComponent(CourseManager.COURSE_PREFIX + "§cClique ici pour la rejoindre.");
                            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gocourse"));
                            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClique ici !").create()));
                            p.spigot().sendMessage(tc);
                            p.sendMessage("§5§m------------------------------");
                        }
                    }

                } else {
                    waitingPlayers.remove(player);
                    player.teleport(CourseManager.getInstance().getTeleportPoint());
                    player.sendMessage(CourseManager.COURSE_PREFIX + "§cTu viens de quitter la partie !");
                }
            } else if (CourseManager.getInstance().getDoorsToStart().stream().anyMatch(location -> block.getLocation().equals(location))) {
                event.setCancelled(true);
            }

        }
    }
}
