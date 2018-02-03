package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownExpBottle;

public class PetsXPBottle extends CustomPet{

	/**
	 * Not sure it is working
	 */
	
	@SuppressWarnings("unchecked")
	public PetsXPBottle() {
		super((Class<? extends LivingEntity>) ThrownExpBottle.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity) {
		// Nothing there yet
	}

}
