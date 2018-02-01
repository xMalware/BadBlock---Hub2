package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEyeEnder extends CustomDisguise
{
	
	public DisguiseEyeEnder(BadblockPlayer player)
	{
		super(player, EntityType.ENDER_SIGNAL);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.PORTAL, 3);
	}

}
