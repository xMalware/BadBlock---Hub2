package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

public class PetsZombie extends CustomPet
{
	
	public PetsZombie()
	{
		super(Zombie.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}