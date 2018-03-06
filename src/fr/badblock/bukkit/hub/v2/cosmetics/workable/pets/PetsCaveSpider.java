package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.LivingEntity;

public class PetsCaveSpider extends CustomPet
{
	
	
	public PetsCaveSpider()
	{
		super(CaveSpider.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}