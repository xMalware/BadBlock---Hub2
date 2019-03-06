package fr.badblock.bukkit.hub.v2.games.blockparty.task;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TimeToMove extends BukkitRunnable {

    private static List<Block> wool = new ArrayList<>();

    @Override
    public void run() {
        if(GameState.WAITING.equals(BlockPartyManager.getInstance().getGameState())  || BlockPartyManager.getInstance().getBlockPlayers().size() <= 0){
            cancel();
            return;
        }
        BlockPartyManager.getInstance().getCuboid().getBlocks().forEach(block -> {
            if(block.getType() == Material.WOOL){
                block.setType(Material.AIR);
                wool.add(block);
            }
        });
        new WaitingTime().runTaskLater(BadBlockHub.getInstance(), 3 * 20);
        this.cancel();
    }

    public static List<Block> getWool() {
        return wool;
    }
}
