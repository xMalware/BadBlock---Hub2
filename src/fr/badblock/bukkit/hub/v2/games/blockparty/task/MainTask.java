package fr.badblock.bukkit.hub.v2.games.blockparty.task;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainTask extends BukkitRunnable {

    // Facteur de diminution de TIME
    public static long K;
    //Temps pour aller sur un bloc (diminue a chaque tour)
    public static long TIME;

    public static int blocksI;
    private static int debug = 50;
    private static List<Block> blocksLeft = new ArrayList<>();

    @Override
    public void run() {
        if(BlockPartyManager.getInstance().getGameState().isState(GameState.WAITING)){
            cancel();
            return;
        }
        long t = (TIME = (TIME - K));
        BlockPartyManager.getInstance().getBlockPlayers().forEach((player, blockPlayer) -> blockPlayer.getRadioSongPlayer().setPlaying(false));
        spawnRandomBlocks();
        blocksI--;
        new TimeToMove().runTaskLater(BadBlockHub.getInstance(), t);
        this.cancel();
    }

    private void spawnRandomBlocks() {
        Random random = new Random();
        List<Block> blocks = BlockPartyManager.getInstance().getCuboid().getBlocks();

        for (int i = 0; i < blocksI; i++) {
            int randomBlocks = random.nextInt(blocks.size());
            Block block = blocks.get(randomBlocks);

            if (blocksLeft.contains(block) || block.getType() != Material.WOOL) {
                i--;
                debug--;
                if(debug <= 0)break;
                continue;
            }

            block.setType(Material.GOLD_BLOCK);
            blocksLeft.add(block);
        }

    }

    public static void resetBlocks(){
        blocksLeft.forEach(b -> b.setType(Material.WOOL));
        blocksLeft.clear();
    }

}
