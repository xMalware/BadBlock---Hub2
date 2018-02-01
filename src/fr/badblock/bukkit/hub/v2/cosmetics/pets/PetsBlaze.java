package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.LivingEntity;

public class PetsBlaze extends CustomPet
{
	
	
	public PetsBlaze()
	{
		super(Blaze.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}