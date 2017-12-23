package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.disguise.Disguise;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMinecartHopper {
	
	private BadblockPlayer player;
	private Disguise disguise;
	
	public DisguiseMinecartHopper(BadblockPlayer player) {
		this.player = player;
		this.disguise = new Disguise(EntityType.MINECART_HOPPER, null, true, true);
	}
	
	public void disguise() {
		this.player.disguise(disguise);
	}
	
	public void undisguise() {
		this.player.undisguise();
	}

}
