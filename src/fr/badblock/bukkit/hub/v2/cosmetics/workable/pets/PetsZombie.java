package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

public class PetsZombie extends CustomPet
{
	
	public PetsZombie()
	{
		super(Zombie.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

	@Override
	public String getSoundSystem() {
		return "ZOMBIE";
	}

}