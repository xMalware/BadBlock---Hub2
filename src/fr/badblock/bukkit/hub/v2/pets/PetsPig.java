package fr.badblock.bukkit.hub.v2.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;

import fr.badblock.gameapi.players.BadblockPlayer;

public class PetsPig {
	
	
	public void deploy(BadblockPlayer player) {
		LivingEntity entity = player.getWorld().spawn(player.getLocation(), Pig.class);
		PetsFollowSystem.followPlayer(player, entity, 1);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void undeploy(BadblockPlayer player) {
		//NOT FOUND for removing it correctly
		player.getWorld().getLivingEntities().remove(PetsFollowSystem.task);
	}

}
