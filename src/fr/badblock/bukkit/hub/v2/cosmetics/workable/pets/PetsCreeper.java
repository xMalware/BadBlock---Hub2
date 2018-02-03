package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;

public class PetsCreeper extends CustomPet
{
	
	
	public PetsCreeper()
	{
		super(Creeper.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}