package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;

public class PetsMagmaCube extends CustomPet
{
	
	
	public PetsMagmaCube()
	{
		super(MagmaCube.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}