package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEndermite extends CustomDisguise
{
	
	public DisguiseEndermite(BadblockPlayer player)
	{
		super(player, EntityType.ENDERMITE);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.PORTAL, 3);
	}

}
