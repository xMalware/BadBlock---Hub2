package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.disguise.Disguise;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEnderCrystal {
	
	private BadblockPlayer player;
	private Disguise disguise;
	
	public DisguiseEnderCrystal(BadblockPlayer player) {
		this.player = player;
		this.disguise = new Disguise(EntityType.ENDER_CRYSTAL, null, true, true);
	}
	
	public void disguise() {
		this.player.disguise(disguise);
	}
	
	public void undisguise() {
		this.player.undisguise();
	}

}
