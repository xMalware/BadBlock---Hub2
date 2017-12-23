package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.disguise.Disguise;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseCreeper {
	
	private BadblockPlayer player;
	private Disguise disguise;
	
	public DisguiseCreeper(BadblockPlayer player) {
		this.player = player;
		this.disguise = new Disguise(EntityType.CREEPER, null, true, true);
	}
	
	public void disguise() {
		this.player.disguise(disguise);
	}
	
	public void undisguise() {
		this.player.undisguise();
	}

}
