package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseSmallFireball extends CustomDisguise
{
	
	public DisguiseSmallFireball(BadblockPlayer player)
	{
		super(player, EntityType.SMALL_FIREBALL);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.FLAME, 1);
	}

}
