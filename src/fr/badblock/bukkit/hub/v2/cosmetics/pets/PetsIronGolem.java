package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;

public class PetsIronGolem extends CustomPet
{
	
	
	public PetsIronGolem()
	{
		super(IronGolem.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}