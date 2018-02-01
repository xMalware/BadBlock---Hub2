package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;

public class PetsGiant extends CustomPet
{
	
	
	public PetsGiant()
	{
		super(Giant.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}