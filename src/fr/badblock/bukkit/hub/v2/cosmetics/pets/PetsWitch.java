package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Witch;

public class PetsWitch extends CustomPet
{
	
	public PetsWitch()
	{
		super(Witch.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}