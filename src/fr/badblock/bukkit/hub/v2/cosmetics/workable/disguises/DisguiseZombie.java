package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseZombie extends CustomDisguise
{
	
	public DisguiseZombie(BadblockPlayer player)
	{
		super(player);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.REDSTONE, 2);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.ZOMBIE;
	}

}
