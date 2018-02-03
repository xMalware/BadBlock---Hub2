package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;

public class PetsDiscoSheep extends CustomPet
{
	
	
	public PetsDiscoSheep()
	{
		super(Sheep.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Set as a disco sheep
		livingEntity.setCustomName("jeb_");
		livingEntity.setCustomNameVisible(false);
	}

}