package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerMoveListener extends BadListener
{

    @EventHandler
    public void whenPlayerMoved(PlayerMoveEvent event)
    {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        autoTeleport(player, event);
    }

    private void autoTeleport(BadblockPlayer player, PlayerMoveEvent event) {
        Location to = event.getTo();
        if (ConfigLoader.getGameHub().isEnabled() && to.getY() <= 75)
        {
            event.setTo(to.getWorld().getSpawnLocation());
            return;
        }
        
        if (to.getY() <= 0) {
            event.setTo(to.getWorld().getSpawnLocation());
        }
    }

}
