package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

public class DisguiseWitch extends CustomDisguise
{
	
	public DisguiseWitch()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SPELL_WITCH, 4);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.WITCH;
	}

}
