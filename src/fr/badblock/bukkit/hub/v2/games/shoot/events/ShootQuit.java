package fr.badblock.bukkit.hub.v2.games.shoot.events;

import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ShootQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        ShootCommand.removePlayerFromShoot(player);
    }

}
