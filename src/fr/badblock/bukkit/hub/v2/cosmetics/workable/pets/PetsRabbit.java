package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Rabbit;

public class PetsRabbit extends CustomPet
{
	
	public PetsRabbit()
	{
		super(Rabbit.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

	@Override
	public String getSoundSystem() {
		return "RABBIT";
	}

}