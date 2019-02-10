package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseBat extends CustomDisguise
{
	
	public DisguiseBat()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SMOKE_NORMAL, 1);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.BAT;
	}
	
}
