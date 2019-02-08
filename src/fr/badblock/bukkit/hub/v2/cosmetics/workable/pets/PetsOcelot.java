package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;

public class PetsOcelot extends CustomPet
{
	
	public PetsOcelot()
	{
		super(Ocelot.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

	@Override
	public String getSoundSystem() {
		return "OCELOT";
	}

}