package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.events.api.PlayerLoadedEvent;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerLoadedListener extends BadListener {

	@EventHandler
	public void onPlayerLoaded(PlayerLoadedEvent event) {
		BadblockPlayer player = event.getPlayer();
		HubPlayer.initialize(player).loadAll();
	}
	
}
