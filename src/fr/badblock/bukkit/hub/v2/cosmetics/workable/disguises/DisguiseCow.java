package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseCow extends CustomDisguise
{
	
	public DisguiseCow(BadblockPlayer player)
	{
		super(player, EntityType.COW);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SNOW_SHOVEL, 2);
	}

}
