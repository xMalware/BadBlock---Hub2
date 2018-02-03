package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseGhast extends CustomDisguise
{
	
	public DisguiseGhast(BadblockPlayer player)
	{
		super(player, EntityType.GHAST);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.LAVA, 3);
	}

}
