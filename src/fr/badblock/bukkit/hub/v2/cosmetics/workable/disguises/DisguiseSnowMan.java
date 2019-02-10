package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseSnowMan extends CustomDisguise
{
	
	public DisguiseSnowMan()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SNOW_SHOVEL, 3);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.SNOWMAN;
	}

}
