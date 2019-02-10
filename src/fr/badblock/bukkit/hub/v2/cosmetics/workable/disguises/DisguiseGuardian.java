package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseGuardian extends CustomDisguise
{
	
	public DisguiseGuardian()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.WATER_BUBBLE, 3);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.GUARDIAN;
	}

}
