package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseSnowball extends CustomDisguise
{
	
	public DisguiseSnowball()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SNOWBALL, 3);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.SNOWBALL;
	}

}
