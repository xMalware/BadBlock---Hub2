package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;

public class PetsGuardian extends CustomPet
{
	
	
	public PetsGuardian()
	{
		super(Guardian.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}