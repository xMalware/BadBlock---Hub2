package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.disguise.Disguise;
import fr.badblock.gameapi.particles.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerMoveListener extends BadListener{
	
	ParticleEffect effect;
	Disguise disguise;
	
	@EventHandler
	public void move(PlayerMoveEvent e) {
		BadblockPlayer player = (BadblockPlayer) e.getPlayer();
		if (player.isDisguised())
		{
		}
	}

}
