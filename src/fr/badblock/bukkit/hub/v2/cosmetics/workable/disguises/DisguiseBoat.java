package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseBoat extends CustomDisguise
{
	
	public DisguiseBoat()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.WATER_BUBBLE, 3);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.BOAT;
	}
	
}
