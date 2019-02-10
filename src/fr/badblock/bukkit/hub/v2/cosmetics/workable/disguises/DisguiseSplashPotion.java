package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseSplashPotion extends CustomDisguise
{
	
	public DisguiseSplashPotion()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.WATER_BUBBLE, 1);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.SPLASH_POTION;
	}

}
