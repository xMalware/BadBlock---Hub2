package fr.badblock.bukkit.hub.v2.games.blockparty.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.badblock.bukkit.hub.v2.games.GamesManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPlayer;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.MainTask;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.bukkit.hub.v2.utils.FeatureUtils;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PartyInteract implements Listener {

    private int i;

    public PartyInteract() {
        i = GamesManager.TIME_TO_START;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.DARK_OAK_FENCE_GATE && BlockPartyManager.getInstance().getGate().equals(block.getLocation())) {
            event.setCancelled(true);

            if (GameState.INGAME.equals(BlockPartyManager.getInstance().getGameState())) {
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cUne partie est déjà en cours. Veuillez attendre qu'elle se termine..");
                return;
            }

            if (JumpManager.getInstance().getJumpPlayers().containsKey(player)) {
                JumpManager.getInstance().getJumpPlayers().remove(player);
                player.sendMessage(JumpManager.JUMP_PREFIX + "§cVous avez quitté le jump.");
                return;
            }

            Map<Player, BlockPlayer> waitingPlayers = BlockPartyManager.getInstance().getBlockPlayers();

            if (!waitingPlayers.containsKey(player)) {
                waitingPlayers.put(player, new BlockPlayer(player, false));
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§bTu as rejoint la partie.");
                FeatureUtils.removeAllFeatures(player);
                player.setAllowFlight(false);
                player.setFlying(false);

                player.teleport(BlockPartyManager.getInstance().getTeleport());

                if (waitingPlayers.size() >= BlockPartyManager.MIN_PLAYER) {
                    player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cLa partie va commencer ! Patientez " + i + "sec...");

                    if (!GameState.STARTING.equals(BlockPartyManager.getInstance().getGameState())) {
                        i = GamesManager.TIME_TO_START;
                        BlockPartyManager.getInstance().setGameState(GameState.STARTING);

                        new BukkitRunnable() {

                            List<Integer> timeToTick = new ArrayList<>(Arrays.asList(60, 30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1));

                            @Override
                            public void run() {
                                if (waitingPlayers.size() < BlockPartyManager.MIN_PLAYER) {
                                    waitingPlayers.forEach((player1, blockPlayer) -> player1.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cNombre de joueur insuffisant !"));
                                    player.sendTitle("§cNombre de joueur insufisant !", "§9Annulation...");
                                    BlockPartyManager.getInstance().setGameState(GameState.WAITING);
                                    cancel();
                                }

                                waitingPlayers.forEach((p, blockPlayer) -> {
                                    if (i != 0) {
                                        p.setLevel(i);
                                        if (timeToTick.contains(i)) {
                                            player.sendTitle("", "§c" + i);
                                            p.sendMessage(BlockPartyManager.BLOCK_PREFIX + "La partie commence dans §c" + i+ " §8secondes");
                                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                                        }
                                    } else {
                                        if (waitingPlayers.size() < BlockPartyManager.MIN_PLAYER) {
                                            waitingPlayers.forEach((player12, blockPlayer1) -> player12.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cNombre de joueur insuffisant !"));
                                            BlockPartyManager.getInstance().setGameState(GameState.WAITING);
                                            cancel();
                                            return;
                                        }

                                        player.sendTitle("", "");
                                        player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "La partie commence !");
                                        BlockPartyManager.getInstance().setGameState(GameState.INGAME);

                                        RadioSongPlayer radioSongPlayer = blockPlayer.getRadioSongPlayer();
                                        radioSongPlayer.setPlaying(true);
                                        MainTask.K = 10;
                                        MainTask.TIME = 4 * 20;
                                        MainTask.blocksI = 15;

                                        cancel();
                                    }

                                });
                                if (i == 0) {
                                    new MainTask().runTaskLater(BadBlockHub.getInstance(), (new Random().nextInt(7) + 3) * 20);
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
                    String key = "Blockparty";

                    if (GlobalFlags.has(key))
                        return;

                    GlobalFlags.set(key, 60000);
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.sendMessage("§5§m------------------------------");
                        p.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§3Un BlockParty va bientôt commencer !");
                        TextComponent tc = new TextComponent(BlockPartyManager.BLOCK_PREFIX + "§cClique ici pour rejoindre la partie.");
                        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goblockparty"));
                        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClique ici !").create()));
                        p.spigot().sendMessage(tc);
                        p.sendMessage("§5§m------------------------------");
                    }
                }

            } else {
                waitingPlayers.remove(player);
                player.teleport(BlockPartyManager.getInstance().getTeleportPoint());
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cTu viens de quitter la partie !");
            }

        }

    }
}
