	package fr.badblock.bukkit.hub.v2.games.blockparty.events;

import java.util.stream.Collectors;

import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPlayer;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.MainTask;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PartyMove implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if (BlockPartyManager.getInstance().getBlockPlayers().containsKey(player) && GameState.INGAME.equals(BlockPartyManager.getInstance().getGameState())){
            if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SEA_LANTERN){
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX+"§cNe triche pas !");
                player.teleport(BlockPartyManager.getInstance().getTeleport());
            }

            BlockPlayer bp = BlockPartyManager.getInstance().getBlockPlayers().get(player);

            if ((BlockPartyManager.getInstance().getTeleport().getY() - 1) == player.getLocation().getY() && !bp.isDead()) {
                player.sendMessage(BlockPartyManager.BLOCK_PREFIX + "Vous êtes éliminé !");
                bp.setDead(true);
                bp.getRadioSongPlayer().setPlaying(false);
                BlockPartyManager.getInstance().getBlockPlayers().keySet().forEach(player1 -> player1.sendMessage(BlockPartyManager.BLOCK_PREFIX+"Le joueur "+player.getName()+" a été éliminé !"));
                player.teleport(BlockPartyManager.getInstance().getTeleportPoint());

                if (BlockPartyManager.getInstance().getBlockPlayers().values().stream().filter(blockPlayer -> !blockPlayer.isDead()).collect(Collectors.toList()).size() <= 1) {
                    PartyCommand.checkWin(player);

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
