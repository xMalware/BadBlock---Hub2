package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseSnowball extends CustomDisguise
{
	
	public DisguiseSnowball(BadblockPlayer player)
	{
		super(player, EntityType.SNOWBALL);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SNOWBALL, 3);
	}

}
