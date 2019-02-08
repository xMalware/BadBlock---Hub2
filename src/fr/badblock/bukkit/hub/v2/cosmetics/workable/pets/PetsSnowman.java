package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowman;

public class PetsSnowman extends CustomPet
{
	
	public PetsSnowman()
	{
		super(Snowman.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

	@Override
	public String getSoundSystem() {
		return "SNOWMAN";
	}

}