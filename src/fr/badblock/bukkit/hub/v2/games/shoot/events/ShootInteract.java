package fr.badblock.bukkit.hub.v2.games.shoot.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.games.GamesManager;
import fr.badblock.bukkit.hub.v2.utils.FeatureUtils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.ShootPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Toinetoine1 on 19/01/2019.
 */
public class ShootInteract implements Listener {

    private int i;

    public ShootInteract() {
        i = GamesManager.TIME_TO_START;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if(ShootManager.getInstance().getShootPlayers().containsKey(player) && ShootManager.getInstance().getGameState() != GameState.INGAME){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) entity;
            if(armorStand.getName().equals(ChatColor.stripColor("Odanorr"))){
                event.setCancelled(true);

                if(GameState.INGAME.equals(ShootManager.getInstance().getGameState())) {
                    player.sendMessage(ShootManager.SHOOT_PREFIX + "§cUne partie est en cours. Veuillez attendre qu'elle se termine..");
                    return;
                }

                if(JumpManager.getInstance().getJumpPlayers().containsKey(player)){
                	JumpManager.getInstance().getJumpPlayers().remove(player);
                    player.sendMessage(JumpManager.JUMP_PREFIX + "§cVous avez quitté le jump.");
                    return;
                }

                Map<BadblockPlayer, ShootPlayer> players = ShootManager.getInstance().getShootPlayers();

                if (!players.containsKey(player)) {
                    players.put(player, new ShootPlayer(player, false));
                    player.sendMessage(ShootManager.SHOOT_PREFIX + "§bTu as rejoint la partie.");
                    FeatureUtils.removeAllFeatures(player);

                    ShootPlayer shootPlayer = players.get(player);

                    if(shootPlayer.assignBox()){
                        player.sendMessage(ShootManager.SHOOT_PREFIX+"§bUne box t'a été attribuée !");
                    } else {
                        player.sendMessage(ShootManager.SHOOT_PREFIX+"Erreur ! Aucune box trouvée ! Réessaye plus tard..");
                        players.remove(player);
                        return;
                    }

                    shootPlayer.getCustomPlayerInventory().storeAndClearInventory(player);
                    player.getInventory().setItem(0, new ItemStack(Material.BOW));
                    player.getInventory().setItem(1, new ItemStack(Material.ARROW, 64));

                    if (players.size() >= ShootManager.MIN_PLAYER) {
                        player.sendMessage(ShootManager.SHOOT_PREFIX + "§cLa partie va commencer ! Patientez "+i+"sec...");

                        if(!GameState.STARTING.equals(ShootManager.getInstance().getGameState())){
                            ShootManager.getInstance().setGameState(GameState.STARTING);
                            i = GamesManager.TIME_TO_START;

                            new BukkitRunnable() {

                                List<Integer> timeToTick = new ArrayList<>(Arrays.asList(30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1));

                                @Override
                                public void run() {
                                    if (players.size() < ShootManager.MIN_PLAYER) {
                                        players.forEach((player1, shootPlayer1) -> {
                                            player1.sendMessage(ShootManager.SHOOT_PREFIX + "§cNombre de joueur insuffisant !");
                                            shootPlayer1.getCustomPlayerInventory().restoreInventory(player1);
                                            player1.sendTitle("§cNombre de joueur insuffisant !", "§9Annulation...");
                                        });
                                        ShootManager.getInstance().setGameState(GameState.WAITING);
                                        cancel();
                                    }

                                    players.forEach((p, shootPlayer1) -> {
                                        if (i != 0) {
                                            p.setLevel(i);
                                            if(timeToTick.contains(i)){
                                                p.sendTitle("", "§c" + i);
                                                p.sendMessage(ShootManager.SHOOT_PREFIX+ "La partie commence dans §c" + i+ " §8secondes");
                                                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                                            }
                                        } else {

                                            p.sendTitle("", "");
                                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                            Location location = shootPlayer1.getBox().getParticle();
                                            location.setYaw(180);
                                            p.teleport(location);
                                            p.sendMessage(ShootManager.SHOOT_PREFIX+"§cLa partie commence !");
                                            ShootManager.getInstance().setGameState(GameState.INGAME);
                                            shootPlayer1.getBox().spawnRandomBlocks();

                                            cancel();
                                        }

                                    });

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
                        String key = "Shoot";

                        if(GlobalFlags.has(key))
                            return;

                        GlobalFlags.set(key, 60000);
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if(player.equals(p)) continue;
                            p.sendMessage("§5§m------------------------------");
                            p.sendMessage(ShootManager.SHOOT_PREFIX + "§3Une partie de tir à l'arc va bientôt commencer !");
                            TextComponent tc = new TextComponent(ShootManager.SHOOT_PREFIX + "§cClique ici pour rejoindre la partie.");
                            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goshoot"));
                            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClique ici !").create()));
                            p.spigot().sendMessage(tc);
                            p.sendMessage("§5§m------------------------------");
                        }
                    }

                } else {
                    players.get(player).getCustomPlayerInventory().restoreInventory(player);
                    players.remove(player);
                    player.teleport(ShootManager.getInstance().getTeleportPoint());
                    player.sendMessage(ShootManager.SHOOT_PREFIX + "§cTu viens de quitter la partie !");
                }
            }

        }

    }

}
