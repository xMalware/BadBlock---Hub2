package fr.badblock.bukkit.hub.v2.listeners.players;

import fr.badblock.gameapi.BadListener;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleFlightEvent;


public class PlayerToggleFlightListener extends BadListener {

    @EventHandler
    private void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        final Player p = e.getPlayer();

        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        e.setCancelled(true);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setVelocity(p.getLocation().getDirection().multiply(1.9).setY(1));

    }

}
