package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseEyeEnder extends CustomDisguise
{
	
	public DisguiseEyeEnder()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.PORTAL, 3);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.ENDER_SIGNAL;
	}

}
