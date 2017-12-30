package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguisePigman extends CustomDisguise
{
	
	public DisguisePigman(BadblockPlayer player)
	{
		super(player, EntityType.PIG_ZOMBIE);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.DRIP_LAVA, 5);
	}

}
