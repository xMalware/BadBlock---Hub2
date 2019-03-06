package fr.badblock.bukkit.hub.v2.games.blockparty.events;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.MainTask;
import fr.badblock.bukkit.hub.v2.games.blockparty.task.TimeToMove;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PartyQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        BlockPartyManager.getInstance().getBlockPlayers().remove(player);

        PartyCommand.restartGames();

        PartyCommand.checkWin(player);

    }

}
