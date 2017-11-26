package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerQuitListener extends BadListener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		HubPlayer.get(player).unload();
	}
	
}
