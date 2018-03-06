package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.events.api.PlayerLoadedEvent;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class PlayerLoadedListener extends BadListener
{
	
	@EventHandler
	public void onPlayerLoaded(PlayerLoadedEvent event)
	{
		BadblockPlayer player = event.getPlayer();
		// Initialize hubPlayer
		initialize(player);
		// Broadcast join message
		broadcastJoinMessage(player);
	}
	
	private void initialize(BadblockPlayer player)
	{
		HubPlayer.initialize(player).loadEverything();
	}
	
	private void broadcastJoinMessage(BadblockPlayer player)
	{
		if (player.hasPermission("hub.broadcastjoin"))
		{
			new TranslatableString("hub.players.broadcast", player.getGroupPrefix(), player.getName()).broadcast();
		}
	}
	
}
