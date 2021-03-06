package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguisePigman extends CustomDisguise
{
	
	public DisguisePigman()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.DRIP_LAVA, 5);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.PIG_ZOMBIE;
	}

}
