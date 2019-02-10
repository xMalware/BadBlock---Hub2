package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseItemFrame extends CustomDisguise
{
	
	public DisguiseItemFrame()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.CLOUD, 1);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.ITEM_FRAME;
	}

}
