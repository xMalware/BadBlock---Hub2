package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;

public class PetsSkeleton extends CustomPet
{
	
	public PetsSkeleton()
	{
		super(Skeleton.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}