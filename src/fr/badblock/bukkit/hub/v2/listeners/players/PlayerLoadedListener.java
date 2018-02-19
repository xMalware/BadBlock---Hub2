package fr.badblock.bukkit.hub.v2.listeners.players;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.events.api.PlayerLoadedEvent;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class PlayerLoadedListener extends BadListener
{
	
	ArrayList<BadblockPlayer> players = new ArrayList<>();
	
	@EventHandler
	public void onPlayerLoaded(PlayerLoadedEvent event)
	{
		BadblockPlayer player = event.getPlayer();
		HubPlayer.initialize(player).loadEverything();
		if(player.hasPermission("hub.broadcastjoin")) {
			new TranslatableString("hub.broadcast", player.getGroupPrefix(), player.getName()).broadcast();
		} else {
			Bukkit.broadcastMessage(null);
		}
	}
	
}
