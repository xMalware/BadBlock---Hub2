package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.entity.Endermite;
import org.bukkit.entity.LivingEntity;

public class PetsEndermite extends CustomPet
{
	
	public PetsEndermite()
	{
		super(Endermite.class, true);
	}

	@Override
	public void onSpawn(LivingEntity livingEntity)
	{
		// Nothing there yet
	}

	@Override
	public String getSoundSystem() {
		return "ENDERMITE";
	}

}