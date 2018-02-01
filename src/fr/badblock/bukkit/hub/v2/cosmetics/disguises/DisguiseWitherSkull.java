package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseWitherSkull extends CustomDisguise
{
	
	public DisguiseWitherSkull(BadblockPlayer player)
	{
		super(player, EntityType.WITHER_SKULL);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return new CustomDisguiseEffect(ParticleEffectType.ENCHANTMENT_TABLE, 3);
	}

}
