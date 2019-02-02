package fr.badblock.bukkit.hub.v2.games.blockparty.task;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class WaitingTime extends BukkitRunnable {

    @Override
    public void run() {
        if(GameState.WAITING.equals(BlockPartyManager.getInstance().getGameState())){
            cancel();
            return;
        }
        Random random = new Random();
        TimeToMove.getWool().forEach(block -> block.setType(Material.WOOL));
        MainTask.resetBlocks();
        BlockPartyManager.getInstance().getBlockPlayers().forEach((player, blockPlayer) -> blockPlayer.getRadioSongPlayer().setPlaying(true));
        new MainTask().runTaskLater(BadBlockHub.getInstance(), (random.nextInt(7) + 3) * 20);
        this.cancel();
    }
}
