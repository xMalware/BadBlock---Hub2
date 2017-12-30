package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseFirework extends CustomDisguise
{
	
	public DisguiseFirework(BadblockPlayer player)
	{
		super(player, EntityType.FIREWORK);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.FIREWORKS_SPARK, 1);
	}

}
