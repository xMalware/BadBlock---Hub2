package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;

public class PetsBabyOcelot extends CustomPet
{
	
	
	public PetsBabyOcelot()
	{
		super(Ocelot.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Set as a baby
		((Ocelot)livingEntity).setBaby();
	}

}