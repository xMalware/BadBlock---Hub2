package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseEndermite extends CustomDisguise
{
	
	public DisguiseEndermite()
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
		return EntityType.ENDERMITE;
	}

}
