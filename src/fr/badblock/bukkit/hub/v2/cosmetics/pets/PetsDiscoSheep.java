package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PetsDiscoSheep extends PetsFollowSystem{
	
	Sheep sheep;
	
	public void deploy(BadblockPlayer player) {
		LivingEntity entity = player.getWorld().spawn(player.getLocation(), Sheep.class);
		sheep.setCustomName("jeb_");
		sheep.setCustomNameVisible(false);
		followPlayer(player, entity, 1);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void undeploy(BadblockPlayer player) {
		//NOT FOUND for removing it correctly
		player.getWorld().getLivingEntities().remove(PetsFollowSystem.task);
	}

}
