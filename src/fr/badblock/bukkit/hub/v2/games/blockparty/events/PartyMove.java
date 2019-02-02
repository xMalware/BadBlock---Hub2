package fr.badblock.bukkit.hub.v2.games.blockparty.events;

import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.MainTask;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collections;
import java.util.stream.Collectors;

public class PartyMove implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if (BlockPartyManager.getInstance().getBlockPlayers().containsKey(player) && GameState.INGAME.equals(BlockPartyManager.getInstance().getGameState())){
            if ((BlockPartyManager.getInstance().getTeleport().getY() - 1) == player.getLocation().getY()) {
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "Vous êtes éliminé !");
                BlockPartyManager.getInstance().getBlockPlayers().get(player).setDead(true);
                BlockPartyManager.getInstance().getBlockPlayers().keySet().forEach(player1 -> player1.sendMessage(BlockPartyManager.BLOCK_PREFIX+"Le joueur "+player.getName()+" a été éliminé !"));
                player.performCommand("spawn");

                if (BlockPartyManager.getInstance().getBlockPlayers().values().stream().filter(blockPlayer -> !blockPlayer.isDead()).collect(Collectors.toList()).size() <= 1) {
                    if (BlockPartyManager.getInstance().getBlockPlayers().values().stream().anyMatch(b -> !b.isDead())) {
                        BlockPartyManager.getInstance().getBlockPlayers().forEach((player1, blockPlayer) -> {
                            player1.sendMessage(BlockPartyManager.BLOCK_PREFIX + "Le joueur " +
                                    BlockPartyManager.getInstance().getBlockPlayers().values().stream().filter(b -> !b.isDead()).findFirst().get().getPlayer().getName() +
                                    " gagne le BlockParty !");
                            player1.performCommand("spawn");
                        });
                    }

                    MainTask.resetBlocks();
                    BlockPartyManager.getInstance().setGameState(GameState.WAITING);
                    BlockPartyManager.getInstance().getBlockPlayers().clear();

                    BlockPartyManager.getInstance().getCuboid().getBlocks().forEach(block -> {
                        if (block.getType() == Material.AIR) {
                            block.setType(Material.WOOL);
                        }
                    });
                }
            }
        }

    }

}
