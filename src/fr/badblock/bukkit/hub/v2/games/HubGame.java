package fr.badblock.bukkit.hub.v2.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.game.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubGame implements Listener
{

	private Map<String, BadblockPlayer> players = new HashMap<>();
	
	private GameState					gameState = GameState.WAITING;
	
	public HubGame()
	{
		Bukkit.getPluginManager().registerEvents(this, BadBlockHub.getInstance());
	}
	
	public void addPlayer(BadblockPlayer player)
	{
		players.put(player.getName().toLowerCase(), player);
	}
	
	public void remove(BadblockPlayer player)
	{
		players.remove(player.getName().toLowerCase());
	}
	
	public List<BadblockPlayer> getPlayers()
	{
		return new ArrayList<>(players.values());
	}
	
	public GameState getState()
	{
		return gameState;
	}
	
	public void setState(GameState gameState)
	{
		this.gameState = gameState;
	}

}
