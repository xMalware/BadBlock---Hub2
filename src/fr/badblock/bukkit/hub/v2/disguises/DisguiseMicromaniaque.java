package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMicromaniaque extends CustomDisguise
{
	/**
	 * For Staff
	 */
	
	BadblockPlayer player;
	public DisguiseMicromaniaque(BadblockPlayer player)
	{
		super(player, EntityType.PLAYER);
		player.setCustomName("micro_maniaque");
		player.setCustomNameVisible(false);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.LAVA, 4);
	}

}
