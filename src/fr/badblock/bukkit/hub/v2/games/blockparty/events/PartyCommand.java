package fr.badblock.bukkit.hub.v2.games.blockparty.events;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.GamesManager;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.MainTask;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.TimeToMove;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.Box;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.ShootPlayer;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PartyCommand implements Listener {

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (BlockPartyManager.getInstance().getBlockPlayers().containsKey(player)) {
            ArrayList<String> cmds = new ArrayList<>(Arrays.asList("/hub", "/lobby", "/spawn"));
            if (cmds.contains(event.getMessage().toLowerCase())) {
                BlockPartyManager.getInstance().getBlockPlayers().remove(player).getRadioSongPlayer().setPlaying(false);
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "§cVous quittez le BlockParty");

                if (BlockPartyManager.getInstance().getBlockPlayers().values().stream().filter(blockPlayer -> !blockPlayer.isDead()).collect(Collectors.toList()).size() <= 1) {
                    restartGames();
                    checkWin(player);
                }
            }

        }


    }

    static void restartGames() {
        if (BlockPartyManager.getInstance().getBlockPlayers().size() == 0) {
            BlockPartyManager.getInstance().getBlockPlayers().clear();
            BlockPartyManager.getInstance().setGameState(GameState.INGAME);

            new BukkitRunnable() {
                @Override
                public void run() {
                    MainTask.resetBlocks();
                    TimeToMove.getWool().forEach(block -> block.setType(Material.WOOL));
                    BlockPartyManager.getInstance().setGameState(GameState.WAITING);
                    BlockPartyManager.getInstance().getGate().add(0, 1,0).getBlock().setType(Material.AIR);
                }
            }.runTaskLater(BadBlockHub.getInstance(), 100);
        }
    }

    static void checkWin(Player player) {
        if (BlockPartyManager.getInstance().getGameState() == GameState.INGAME && BlockPartyManager.getInstance().getBlockPlayers().values().stream()
                .filter(b -> !b.isDead())
                .collect(Collectors.toList()).size() == 1) {
            BlockPartyManager.getInstance().getBlockPlayers().forEach((player1, blockPlayer) -> {
                player1.sendMessage(BlockPartyManager.BLOCK_PREFIX + "Le joueur " +
                        BlockPartyManager.getInstance().getBlockPlayers().values().stream().filter(b -> !b.isDead()).findFirst().get().getPlayer().getName() +
                        " gagne le BlockParty !");
                player1.teleport(BlockPartyManager.getInstance().getTeleportPoint());
                blockPlayer.getRadioSongPlayer().setPlaying(false);
                blockPlayer.getRadioSongPlayer().removePlayer(player);
            });
            BlockPartyManager.getInstance().setGameState(GameState.WAITING);
            BlockPartyManager.getInstance().getBlockPlayers().clear();
            BlockPartyManager.getInstance().getGate().add(0, 1,0).getBlock().setType(Material.BARRIER);
        }
    }

}
