package fr.badblock.bukkit.hub.v2.listeners.players;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

import java.util.HashMap;

public class PlayerQuitListener extends BadListener
{
	HubGame game;
	public static HashMap<HubGame, BadblockPlayer> hubGame = new HashMap<>();

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		// Remove default quit message
		removeDefaultQuitMessage(event);
		// Unload player
		unloadPlayer(player);
		//Remove player into the Game if he leave the Hub
		if(hubGame.containsKey(player)){
			game.remove(player);
		}
	}
	
	private void unloadPlayer(BadblockPlayer player)
	{
		HubPlayer.get(player).unload();
	}
	
	private void removeDefaultQuitMessage(PlayerQuitEvent event)
	{
		event.setQuitMessage(null);
	}
	
}
