package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

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

	@Override
	public String getSoundSystem() {
		return "CHICKEN_";
	}

}