package fr.badblock.bukkit.hub.v2.listeners.players;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerToggleSneakListener extends BadListener
{
	
	public static HashMap<BadblockPlayer, Entity> mount = new HashMap<>();
	
	@EventHandler
	public void sneak(PlayerToggleSneakEvent event)
	{
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		if (mount.containsKey(player))
		{
			mount.get(player).remove();
			mount.remove(player);
		}
	}

}