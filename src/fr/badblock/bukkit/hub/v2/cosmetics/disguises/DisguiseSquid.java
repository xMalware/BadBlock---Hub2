package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseSquid extends CustomDisguise
{
	
	public DisguiseSquid(BadblockPlayer player)
	{
		super(player, EntityType.SQUID);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.WATER_BUBBLE, 1);
	}

}
