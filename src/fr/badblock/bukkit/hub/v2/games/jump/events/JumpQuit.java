package fr.badblock.bukkit.hub.v2.games.jump.events;

import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Toinetoine1 on 16/01/2019.
 */
public class JumpQuit {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        JumpManager.getInstance().getJumpPlayers().remove(player);
    }

}
