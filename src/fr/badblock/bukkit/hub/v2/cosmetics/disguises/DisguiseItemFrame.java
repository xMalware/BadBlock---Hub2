package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseItemFrame extends CustomDisguise
{
	
	public DisguiseItemFrame(BadblockPlayer player)
	{
		super(player, EntityType.ITEM_FRAME);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.CLOUD, 1);
	}

}
