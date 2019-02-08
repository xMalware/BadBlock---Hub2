package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;

public class PetsEnderman extends CustomPet
{
	
	
	public PetsEnderman()
	{
		super(Enderman.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

	@Override
	public String getSoundSystem() {
		return "ENDERMAN_";
	}

}