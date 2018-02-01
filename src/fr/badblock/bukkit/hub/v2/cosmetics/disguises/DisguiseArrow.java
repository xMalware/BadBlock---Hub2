package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseArrow extends CustomDisguise
{
	
	public DisguiseArrow(BadblockPlayer player)
	{
		super(player, EntityType.ARROW);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.FLAME, 1);
	}

}
