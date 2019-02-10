package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseFirework extends CustomDisguise
{
	
	public DisguiseFirework()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.FIREWORKS_SPARK, 1);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.FIREWORK;
	}

}
