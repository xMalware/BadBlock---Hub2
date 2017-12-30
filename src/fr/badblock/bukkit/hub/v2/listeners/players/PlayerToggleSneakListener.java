package fr.badblock.bukkit.hub.v2.listeners.players;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerToggleSneakListener extends BadListener{
	
	public static HashMap<BadblockPlayer, Entity> mount = new HashMap<>();
	
	@EventHandler
	public void sneak(PlayerToggleSneakEvent e) {
		BadblockPlayer p = (BadblockPlayer) e.getPlayer();
		if(mount.containsKey(p)) {
			mount.get(p).remove();
			mount.remove(p);
		}
	}

}
