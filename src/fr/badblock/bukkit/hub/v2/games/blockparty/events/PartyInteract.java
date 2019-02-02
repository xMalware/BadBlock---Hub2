package fr.badblock.bukkit.hub.v2.games.blockparty.events;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPlayer;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.MainTask;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PartyInteract implements Listener {

    private Map<Player, Timestamp> time = new HashMap<>();

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onInteract(PlayerInteractEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if(action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.DARK_OAK_FENCE_GATE && BlockPartyManager.getInstance().getGate().equals(block.getLocation())){
            event.setCancelled(true);

            if (GameState.INGAME.equals(BlockPartyManager.getInstance().getGameState())) {
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cLa partie à déjà commencé ! Veuillez attendre qu'elle se termine..");
                return;
            }

            if(JumpManager.getInstance().getJumpPlayers().containsKey(player)){
                player.sendMessage(JumpManager.JUMP_PREFIX + "§cVous quittez le jump..");
                return;
            }

             Map<Player, BlockPlayer> waitingPlayers = BlockPartyManager.getInstance().getBlockPlayers();

            if (!waitingPlayers.containsKey(player)) {
                Timestamp ts = new Timestamp(System.currentTimeMillis());

                if(time.containsKey(player) && time.get(player).after(ts)){
                    player.sendMessage("§cTu dois attendre 30 secondes avant de pouvoir rentre à nouveau (Anti-Spam)");
                    return;
                }

                waitingPlayers.put(player, new BlockPlayer(player, false));
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§bTu rejoins la partie !");

                // TODO REWRITE THIS! I'LL SHAKE!
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                timestamp.setSeconds(timestamp.getSeconds() + 30);
                time.put(player, timestamp);

                player.teleport(BlockPartyManager.getInstance().getTeleport());

                if (waitingPlayers.size() >= BlockPartyManager.MIN_PLAYER) {
                    player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cLa partie va commencer ! Attendre 60sec...");
                    
                    if(!GameState.STARTING.equals(BlockPartyManager.getInstance().getGameState())){
                        BlockPartyManager.getInstance().setGameState(GameState.STARTING);

                        new BukkitRunnable() {

                            int i = 10;
                            List<Integer> timeToTick = new ArrayList<>(Arrays.asList(60, 30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1));

                            @Override
                            public void run() {
                                if (waitingPlayers.size() < BlockPartyManager.MIN_PLAYER) {
                                    waitingPlayers.forEach((player1, blockPlayer) -> player1.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cNombre de joueur insufisant !"));
                                    player.sendTitle("§cNombre de joueur insufisant !", "§9Annulation...");
                                    BlockPartyManager.getInstance().setGameState(GameState.WAITING);
                                    cancel();
                                }

                                waitingPlayers.forEach((p, blockPlayer) -> {
                                    p.setLevel(i);
                                    if (i != 0) {
                                        if (timeToTick.contains(i)) {
                                            player.sendTitle("", "§c" + i);
                                            p.sendMessage(BlockPartyManager.BLOCK_PREFIX+"La partie commence dans §c"+i);
                                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                                        }
                                    } else {
                                        if (waitingPlayers.size() < BlockPartyManager.MIN_PLAYER) {
                                            waitingPlayers.forEach((player12, blockPlayer1) -> player12.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cNombre de joueur insufisant !"));
                                            BlockPartyManager.getInstance().setGameState(GameState.WAITING);
                                            cancel();
                                            return;
                                        }

                                        player.sendTitle("", "");
                                        player.sendMessage(BlockPartyManager.BLOCK_PREFIX+"La partie commence !");
                                        BlockPartyManager.getInstance().setGameState(GameState.INGAME);

                                        RadioSongPlayer radioSongPlayer = blockPlayer.getRadioSongPlayer();
                                        radioSongPlayer.setPlaying(true);
                                        MainTask.K = 10;
                                        MainTask.TIME = 4 * 20;
                                        MainTask.blocksI = 15;

                                        cancel();
                                    }

                                });
                                if(i == 0){
                                    new MainTask().runTaskLater(BadBlockHub.getInstance(), (new Random().nextInt(7) + 3) * 20);
                                }

                                if (i <= 0)
                                    cancel();

                                i--;
                            }

                        }.runTaskTimer(BadBlockHub.getInstance(), 0, 20);
                    }
                } else {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.sendMessage("§5§m------------------------------");
                        p.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§3Un BlockParty va bientôt commencer !");
                        p.sendMessage("§5§m------------------------------");
                    }
                }

            } else {
                waitingPlayers.remove(player);
                player.performCommand("spawn");
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cTu viens de quitter la partie !");
            }

        }

    }
}
