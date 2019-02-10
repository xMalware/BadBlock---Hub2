package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseMagmaCube extends CustomDisguise
{
	
	public DisguiseMagmaCube()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.FLAME, 2);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.MAGMA_CUBE;
	}

}
