package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.LivingEntity;

public class PetsChicken extends CustomPet
{
	
	
	public PetsChicken()
	{
		super(Chicken.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}