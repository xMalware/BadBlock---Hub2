package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseSlime extends CustomDisguise
{
	
	public DisguiseSlime()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SLIME, 5);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.SLIME;
	}

}
