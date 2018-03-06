package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.LivingEntity;

public class PetsEnderpearl extends CustomPet{

	/**
	 * Not sure it is working
	 */
	
	@SuppressWarnings("unchecked")
	public PetsEnderpearl() {
		super((Class<? extends LivingEntity>) EnderPearl.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity) {
		// Nothing there yet
	}

}
