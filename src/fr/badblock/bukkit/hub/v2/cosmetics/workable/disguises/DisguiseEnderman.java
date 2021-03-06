package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseEnderman extends CustomDisguise
{
	
	public DisguiseEnderman()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.PORTAL, 1);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.ENDERMAN;
	}

}
