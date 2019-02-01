package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Toinetoine1 on 17/01/2019.
 */
public class SpleefQuit implements Listener {


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        SpleefManager.getInstance().getSpleefPlayers().remove(player);

        if (SpleefManager.getInstance().getSpleefPlayers().size() == 0) {
            SpleefManager.getInstance().getGameState().setState(GameState.WAITING);
            SpleefBreak.restoreBlocks();
        }
    }

}
