package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;

public class PetsHorse extends CustomPet
{
	
	
	public PetsHorse()
	{
		super(Horse.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}