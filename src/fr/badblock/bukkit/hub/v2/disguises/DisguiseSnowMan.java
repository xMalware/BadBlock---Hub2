package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseSnowMan extends CustomDisguise
{
	
	public DisguiseSnowMan(BadblockPlayer player)
	{
		super(player, EntityType.SNOWMAN);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.SNOW_SHOVEL, 3);
	}

}
