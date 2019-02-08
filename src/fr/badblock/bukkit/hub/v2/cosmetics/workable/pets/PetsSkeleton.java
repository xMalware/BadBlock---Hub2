package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

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

	@Override
	public String getSoundSystem() {
		return "SKELETON";
	}

}