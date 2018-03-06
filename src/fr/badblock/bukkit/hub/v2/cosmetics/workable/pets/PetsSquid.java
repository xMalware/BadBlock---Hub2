package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Squid;

public class PetsSquid extends CustomPet
{
	
	public PetsSquid()
	{
		super(Squid.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}