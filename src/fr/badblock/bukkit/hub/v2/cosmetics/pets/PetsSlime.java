package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PetsSlime extends PetsFollowSystem{
	
	Slime slime;
	
	/**
	 * Deploy Slime Mount
	 * @param player
	 *       Player receiving
	 * @param arg0
	 *       size of the Slime
	 */
	
	public void deploy(BadblockPlayer player, int arg0) {
		LivingEntity entity = player.getWorld().spawn(player.getLocation(), Slime.class);
		slime.setSize(arg0);
		followPlayer(player, entity, 1);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void undeploy(BadblockPlayer player) {
		//NOT FOUND for removing it correctly
		player.getWorld().getLivingEntities().remove(PetsFollowSystem.task);
	}

}
