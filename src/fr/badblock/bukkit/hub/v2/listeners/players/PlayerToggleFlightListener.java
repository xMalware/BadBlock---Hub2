package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import fr.badblock.gameapi.BadListener;


public class PlayerToggleFlightListener extends BadListener {

   // private HashMap<Player, Integer> jumps = new HashMap<>();

    @EventHandler
    private void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        //TODO Double jump :)

    }

}
