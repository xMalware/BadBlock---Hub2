package fr.badblock.bukkit.hub.v2.games.course.events;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.course.task.CourseGameRunnable;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */

public class CourseInteract implements Listener {

    static CourseGameRunnable runnable;
    private Map<Player, Timestamp> time = new HashMap<>();

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (block != null && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.SPRUCE_FENCE_GATE) {
            if (CourseManager.getInstance().getDoorsToEnter().keySet().stream().anyMatch(location -> block.getLocation().equals(location))) {
                event.setCancelled(true);

                if (CourseManager.getInstance().getState().isState(GameState.INGAME)) {
                    player.sendMessage(CourseManager.COURSE_PREFIX + "§cLa partie à déjà commencé ! Veuillez attendre qu'elle se termine..");
                    return;
                }

                if(JumpManager.getInstance().getJumpPlayers().containsKey(player)){
                    player.sendMessage(JumpManager.JUMP_PREFIX + "§cVous quittez le jump..");
                    return;
                }

                List<BadblockPlayer> waitingPlayers = CourseManager.getInstance().getWaitingPlayers();

                if (!waitingPlayers.contains(player)) {
                    Timestamp ts = new Timestamp(System.currentTimeMillis());

                    if(time.containsKey(player) && time.get(player).after(ts)){
                        player.sendMessage("§cTu dois attendre 30 secondes avant de pouvoir rentre à nouveau (Anti-Spam)");
                        return;
                    }

                    waitingPlayers.add(player);
                    player.sendMessage(CourseManager.COURSE_PREFIX + "§bTu rejoins la partie !");
                    CourseManager.getInstance().getDoorsToEnter().replace(block.getLocation(), true);

                    // TODO REWRITE THIS! I'LL SHAKE!
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    timestamp.setSeconds(timestamp.getSeconds() + 30);
                    time.put(player, timestamp);

                    int pos = 0;
                    for(int i = 0; i < CourseManager.getInstance().getDoorsToEnter().entrySet().size(); i++){
                        if(CourseManager.getInstance().getDoorsToEnter().keySet().toArray()[i].equals(block.getLocation())){
                            pos = i;
                            break;
                        }
                    }

                    player.teleport(CourseManager.getInstance().getWaitingPos().get(pos));

                    if (waitingPlayers.size() >= CourseManager.MIN_PLAYER) {
                        player.sendMessage(CourseManager.COURSE_PREFIX + "§cLa partie va commencer ! Attendre 60sec...");

                        for (Location loc : CourseManager.getInstance().getDoorsToStart()) {
                            BlockState Gate1 = loc.getBlock().getState();
                            Openable openable1 = (Openable) Gate1.getData();
                            openable1.setOpen(false);
                            Gate1.setData((MaterialData) openable1);
                            Gate1.update();
                        }

                        if(!CourseManager.getInstance().getState().isState(GameState.STARTING)){
                            CourseManager.getInstance().getState().setState(GameState.STARTING);

                            new BukkitRunnable() {

                                int i = 60;
                                List<Integer> timeToTick = new ArrayList<>(Arrays.asList(60, 30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1));

                                @Override
                                public void run() {
                                    if (waitingPlayers.size() < CourseManager.MIN_PLAYER) {
                                        waitingPlayers.forEach(player -> player.sendMessage(CourseManager.COURSE_PREFIX + "§cNombre de joueur insufisant !"));
                                        player.sendTitle("§cNombre de joueur insufisant !", "§9Annulation...");
                                        CourseManager.getInstance().getState().setState(GameState.WAITING);
                                        cancel();
                                    }

                                    waitingPlayers.forEach(p -> {
                                        p.setLevel(i);
                                        if (i != 0) {
                                            if(timeToTick.contains(i)){
                                                p.sendTitle("", "§c" + i);
                                                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                                            }
                                        } else {
                                            if (waitingPlayers.size() < CourseManager.MIN_PLAYER) {
                                                waitingPlayers.forEach(player -> player.sendMessage(CourseManager.COURSE_PREFIX + "§cNombre de joueur insufisant !"));
                                                CourseManager.getInstance().getState().setState(GameState.WAITING);
                                                cancel();
                                                return;
                                            }

                                            p.sendTitle("", "");
                                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                            CourseManager.getInstance().getState().setState(GameState.INGAME);
                                            runnable = new CourseGameRunnable();
                                            runnable.runTaskTimerAsynchronously(BadBlockHub.getInstance(), 0, 20);

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

                                    if (i <= 0)
                                        cancel();

                                    i--;
                                }

                            }.runTaskTimer(BadBlockHub.getInstance(), 0, 20);
                        }
                    } else {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.sendMessage("§5§m------------------------------");
                            p.sendMessage(CourseManager.COURSE_PREFIX + "§3Une course va bientôt commencer !");
                            //Bukkit.broadcastMessage(coursePrefix + "§cClique ici pour la rejoindre.");
                            TextComponent tc = new TextComponent(CourseManager.COURSE_PREFIX + "§cClique ici pour la rejoindre.");
                            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gocourse"));
                            p.spigot().sendMessage(tc);
                            p.sendMessage("§5§m------------------------------");
                        }
                    }

                } else {
                    waitingPlayers.remove(player);
                    player.performCommand("spawn");
                    player.sendMessage(CourseManager.COURSE_PREFIX + "§cTu viens de quitter la partie !");
                    CourseManager.getInstance().getDoorsToEnter().put(block.getLocation(), false);
                }
            } else if (CourseManager.getInstance().getDoorsToStart().stream().anyMatch(location -> block.getLocation().equals(location))) {
                event.setCancelled(true);
            }

        }
    }
}
