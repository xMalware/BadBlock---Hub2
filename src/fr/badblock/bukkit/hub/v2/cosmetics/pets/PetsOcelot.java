package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;

public class PetsOcelot extends CustomPet
{
	
	
	public PetsOcelot()
	{
		super(Ocelot.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}