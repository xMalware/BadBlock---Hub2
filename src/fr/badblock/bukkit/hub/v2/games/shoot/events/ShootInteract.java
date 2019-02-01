package fr.badblock.bukkit.hub.v2.games.shoot.events;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.ShootPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Toinetoine1 on 19/01/2019.
 */
public class ShootInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) entity;
            if(armorStand.getName().equals(ChatColor.stripColor("Odanorr"))){
                event.setCancelled(true);

                if(ShootManager.getInstance().getGameState().isState(GameState.INGAME)) {
                    player.sendMessage(ShootManager.SHOOT_PREFIX + "§cLa partie à déjà commencé ! Veuillez attendre qu'elle se termine..");
                    return;
                }

                if(JumpManager.getInstance().getJumpPlayers().containsKey(player)){
                    player.sendMessage(JumpManager.JUMP_PREFIX + "§cVous quittez le jump..");
                    return;
                }

                Map<BadblockPlayer, ShootPlayer> players = ShootManager.getInstance().getShootPlayers();

                if (!players.containsKey(player)) {
                    players.put((BadblockPlayer) player, new ShootPlayer(player, false));
                    player.sendMessage(ShootManager.SHOOT_PREFIX + "§bTu rejoins la partie !");

                    ShootPlayer shootPlayer = players.get(player);

                    if(shootPlayer.assignBox()){
                        player.sendMessage(ShootManager.SHOOT_PREFIX+"§bUne box t'as été attribué !");
                    } else {
                        player.sendMessage(ShootManager.SHOOT_PREFIX+"Erreur ! Aucune box trouvé ! Rééssayez plus tard..");
                        players.remove(player);
                        return;
                    }

                    shootPlayer.getCustomPlayerInventory().storeAndClearInventory(player);
                    player.getInventory().setItem(0, new ItemStack(Material.BOW));
                    player.getInventory().setItem(1, new ItemStack(Material.ARROW, 64));

                    if (players.size() >= ShootManager.MIN_PLAYER) {
                        player.sendMessage(ShootManager.SHOOT_PREFIX + "§cLa partie va commencer ! Attendre 60sec...");


                        if(!ShootManager.getInstance().getGameState().isState(GameState.STARTING)){
                            ShootManager.getInstance().getGameState().setState(GameState.STARTING);

                            new BukkitRunnable() {

                                int i = 61;
                                List<Integer> timeToTick = new ArrayList<>(Arrays.asList(60, 30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1));

                                @Override
                                public void run() {
                                    if (players.size() < ShootManager.MIN_PLAYER) {
                                        players.forEach((player1, shootPlayer1) -> {
                                            player1.sendMessage(ShootManager.SHOOT_PREFIX + "§cNombre de joueur insufisant !");
                                            shootPlayer1.getCustomPlayerInventory().restoreInventory(player1);
                                            player1.sendTitle("§cNombre de joueur insufisant !", "§9Annulation...");
                                        });
                                        ShootManager.getInstance().getGameState().setState(GameState.WAITING);
                                        cancel();
                                    }

                                    players.forEach((p, shootPlayer1) -> {
                                        p.setLevel(i);
                                        if (i != 0) {
                                            if(timeToTick.contains(i)){
                                                p.sendTitle("", "§c" + i);
                                                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                                            }
                                        } else {
                                            p.sendTitle("", "");
                                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                            p.teleport(shootPlayer1.getBox().getParticle());
                                            ShootManager.getInstance().getGameState().setState(GameState.INGAME);
                                            shootPlayer1.getBox().spawnRandomBlocks();

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
                            p.sendMessage(ShootManager.SHOOT_PREFIX + "§3Une partie de tir à l'arc va bientôt commencer !");
                            TextComponent tc = new TextComponent(ShootManager.SHOOT_PREFIX + "§cClique ici pour la rejoindre.");
                            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goshoot"));
                            p.spigot().sendMessage(tc);
                            p.sendMessage("§5§m------------------------------");
                        }
                    }

                } else {
                    players.get(player).getCustomPlayerInventory().restoreInventory(player);
                    players.remove(player);
                    player.performCommand("spawn");
                    player.sendMessage(ShootManager.SHOOT_PREFIX + "§cTu viens de quitter la partie !");
                }
            }

        }

    }

}
