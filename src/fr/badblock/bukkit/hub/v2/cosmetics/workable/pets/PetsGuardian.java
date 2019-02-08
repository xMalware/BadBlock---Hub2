package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

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

	@Override
	public String getSoundSystem() {
		return "GUARDIAN";
	}

}