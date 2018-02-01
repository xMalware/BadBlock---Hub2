package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMagmaCube extends CustomDisguise
{
	
	public DisguiseMagmaCube(BadblockPlayer player)
	{
		super(player, EntityType.MAGMA_CUBE);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.FLAME, 2);
	}

}
