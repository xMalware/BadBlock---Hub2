package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseBoat extends CustomDisguise
{
	
	public DisguiseBoat(BadblockPlayer player)
	{
		super(player, EntityType.BOAT);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.WATER_BUBBLE, 3);
	}
	
}
