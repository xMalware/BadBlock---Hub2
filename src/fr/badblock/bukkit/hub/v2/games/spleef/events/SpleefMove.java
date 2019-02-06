package fr.badblock.bukkit.hub.v2.games.spleef.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.games.utils.ItemBuilder;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.selections.CuboidSelection;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Toinetoine1 on 17/01/2019.
 */
public class SpleefMove implements Listener {

    private CuboidSelection selection;

    public SpleefMove(Location loc1, Location loc2) {
        selection = new CuboidSelection(loc1, loc2);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if (selection.isInSelection(player.getLocation())) {
            if (GameState.WAITING.equals(SpleefManager.getInstance().getGameState())) {
                if (!SpleefManager.getInstance().getSpleefPlayers().containsKey(player)) {
                    if (JumpManager.getInstance().getJumpPlayers().containsKey(player)) {
                        player.sendMessage(JumpManager.JUMP_PREFIX + "§cVous quittez le jump..");
                        return;
                    }

                    SpleefManager.getInstance().getSpleefPlayers().put(player, new SpleefPlayer(player.getName(), player.getGameMode() == GameMode.CREATIVE));
                    player.sendMessage(SpleefManager.SPLEEF_PREFIX + "Vous rejoignez le Spleef !");
                    player.setGameMode(GameMode.ADVENTURE);

                    TextComponent quitComponent = new TextComponent(SpleefManager.SPLEEF_PREFIX + "§c[Quittez le Spleef]");
                    quitComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/quitspleef"));
                    quitComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClique ici !").create()));
                    player.spigot().sendMessage(quitComponent);

                    SpleefManager.getInstance().getSpleefPlayers().get(player).getCustomInv().storeAndClearInventory(player);

                    ItemStack shovel = new ItemBuilder(Material.DIAMOND_SPADE).setUnbreakable(true).toItemStack();
                    player.getInventory().setItem(4, shovel);

                    if (SpleefManager.getInstance().getSpleefPlayers().size() >= SpleefManager.MIN_PLAYER) {
                        player.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cLa partie va commencer ! Attendre 60sec...");

                        if (!GameState.STARTING.equals(SpleefManager.getInstance().getGameState())) {
                            SpleefManager.getInstance().setGameState(GameState.STARTING);

                            new BukkitRunnable() {

                                int i = 60;
                                List<Integer> timeToTick = new ArrayList<>(Arrays.asList(60, 30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1));

                                @SuppressWarnings("deprecation")
								@Override
                                public void run() {
                                    if (SpleefManager.getInstance().getSpleefPlayers().size() < SpleefManager.MIN_PLAYER) {
                                        SpleefManager.getInstance().getSpleefPlayers().forEach((player, spleefPlayer) -> {
                                            player.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cNombre de joueur insufisant !");
                                            player.performCommand("spawn");
                                            spleefPlayer.getCustomInv().restoreInventory(player);
                                        });
                                        player.sendTitle("§cNombre de joueur insufisant !", "§9Annulation...");
                                        SpleefManager.getInstance().setGameState(GameState.WAITING);
                                        cancel();
                                    }

                                    SpleefManager.getInstance().getSpleefPlayers().forEach((p, spleefPlayer) -> {
                                        p.setLevel(i);
                                        if (i != 0) {
                                            if (timeToTick.contains(i)) {
                                                p.sendTitle("", "§c" + i);
                                                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                                                p.sendMessage(SpleefManager.SPLEEF_PREFIX+"La partie commence dans §c"+i);
                                            }
                                        } else {
                                            if (SpleefManager.getInstance().getSpleefPlayers().size() < SpleefManager.MIN_PLAYER) {
                                                SpleefManager.getInstance().getSpleefPlayers().forEach((player, spleeffPlayer) -> player.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cNombre de joueur insufisant !"));
                                                SpleefManager.getInstance().setGameState(GameState.WAITING);
                                                cancel();
                                                return;
                                            }

                                            p.setGameMode(GameMode.SURVIVAL);
                                            p.sendMessage(SpleefManager.SPLEEF_PREFIX+"La partie commence !");
                                            p.sendTitle("", "");
                                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                            SpleefManager.getInstance().setGameState(GameState.INGAME);

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
                            p.sendMessage(SpleefManager.SPLEEF_PREFIX + "§3Une partie de Spleef va bientôt commencer !");
                            TextComponent tc = new TextComponent(SpleefManager.SPLEEF_PREFIX + "§cClique ici pour la rejoindre.");
                            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gospleef"));
                            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClique ici !").create()));
                            p.spigot().sendMessage(tc);
                            p.sendMessage("§5§m------------------------------");
                        }
                    }
                }

            } else if (GameState.INGAME.equals(SpleefManager.getInstance().getGameState())) {
                player.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cLa partie à déjà commencé ! Veuillez attendre qu'elle se termine..");
                player.teleport(SpleefManager.getInstance().getTeleportPoint());
            }
        } else if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player) && player.getLocation().getBlock().getType() == Material.STATIONARY_WATER && GameState.INGAME.equals(SpleefManager.getInstance().getGameState())) {
            SpleefManager.getInstance().getSpleefPlayers().get(player).setDead(true);
            List<SpleefPlayer> playersAlive = SpleefManager.getInstance().getSpleefPlayers().values().stream().filter(spleefPlayer -> !spleefPlayer.isDead()).collect(Collectors.toList());

            if (playersAlive.size() == 1) {
                SpleefManager.getInstance().getSpleefPlayers().forEach((p, spleefPlayer) -> {
                    p.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cLe joueur " + playersAlive.get(0).getPlayerName() + " gagne le Spleef !");
                    spleefPlayer.getCustomInv().restoreInventory(p);
                    p.performCommand("spawn");
                    p.setGameMode(GameMode.ADVENTURE);
                });

                SpleefManager.getInstance().getSpleefPlayers().clear();
                SpleefBreak.restoreBlocks();
                SpleefManager.getInstance().setGameState(GameState.WAITING);
            }

        }


    }

}
