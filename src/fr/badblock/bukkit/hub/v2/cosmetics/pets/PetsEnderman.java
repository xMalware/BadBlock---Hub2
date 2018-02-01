package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PetsEnderman extends PetsFollowSystem{
	
	
	public void deploy(BadblockPlayer player) {
		LivingEntity entity = player.getWorld().spawn(player.getLocation(), Enderman.class);
		followPlayer(player, entity, 1);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void undeploy(BadblockPlayer player) {
		//NOT FOUND for removing it correctly
		player.getWorld().getLivingEntities().remove(PetsFollowSystem.task);
	}

}
