package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.Cow;
import org.bukkit.entity.LivingEntity;

public class PetsCow extends CustomPet
{
	
	
	public PetsCow()
	{
		super(Cow.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity) {
		// Nothing there yet
	}

}
