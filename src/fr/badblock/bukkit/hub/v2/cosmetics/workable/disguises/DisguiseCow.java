package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseCow extends CustomDisguise
{
	
	public DisguiseCow()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SNOW_SHOVEL, 2);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.COW;
	}

}
