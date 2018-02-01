package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;

public class PetsZombiePigman extends CustomPet
{
	
	public PetsZombiePigman()
	{
		super(PigZombie.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}