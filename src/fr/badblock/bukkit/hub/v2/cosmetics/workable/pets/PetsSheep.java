package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;

public class PetsSheep extends CustomPet{

	public PetsSheep() {
		super(Sheep.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity) {
		// Nothing there yet
	}

}
