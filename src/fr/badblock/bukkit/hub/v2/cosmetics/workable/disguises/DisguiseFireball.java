package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseFireball extends CustomDisguise
{
	
	public DisguiseFireball()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.FLAME, 3);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.FIREBALL;
	}

}
