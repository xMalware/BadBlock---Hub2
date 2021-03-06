package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseZombie extends CustomDisguise
{
	
	public DisguiseZombie()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.REDSTONE, 2);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.ZOMBIE;
	}

}
