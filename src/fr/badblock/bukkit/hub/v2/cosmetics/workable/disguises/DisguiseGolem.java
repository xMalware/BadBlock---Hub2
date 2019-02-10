package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseGolem extends CustomDisguise
{
	
	public DisguiseGolem()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.LAVA, 2);		
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.IRON_GOLEM;
	}

}
