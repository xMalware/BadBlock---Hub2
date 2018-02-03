package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;

public class PetsSlime extends CustomPet
{
	
	public PetsSlime()
	{
		super(Slime.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

}