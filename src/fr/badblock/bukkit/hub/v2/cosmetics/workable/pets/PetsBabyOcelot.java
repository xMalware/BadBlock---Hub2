package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;

public class PetsBabyOcelot extends CustomPet
{
	
	
	public PetsBabyOcelot()
	{
		super(Ocelot.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Set as a baby
		((Ocelot)livingEntity).setBaby();
	}

	@Override
	public String getSoundSystem() {
		return "OCELOT";
	}

}