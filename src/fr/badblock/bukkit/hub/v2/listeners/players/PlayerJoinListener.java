package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.badblock.gameapi.BadListener;

public class PlayerJoinListener extends BadListener
{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		removeDefaultJoinMessage(event);
	}
	
	private void removeDefaultJoinMessage(PlayerJoinEvent event)
	{
		event.setJoinMessage(null);
	}
	
}
