package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseBat extends CustomDisguise
{
	
	public DisguiseBat(BadblockPlayer player)
	{
		super(player, EntityType.BAT);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.SMOKE_NORMAL, 1);
	}
	
}
