package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEnderman extends CustomDisguise
{
	
	public DisguiseEnderman(BadblockPlayer player)
	{
		super(player, EntityType.ENDERMAN);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.PORTAL, 1);
	}

}
