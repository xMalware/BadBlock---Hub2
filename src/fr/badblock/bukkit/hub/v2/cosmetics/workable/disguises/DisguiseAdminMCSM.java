package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseAdminMCSM extends CustomDisguise
{
	/**
	 * Not sure it is working.
	 */
	
	BadblockPlayer player;
	
	public DisguiseAdminMCSM(BadblockPlayer player)
	{
		super(player);
		player.setCustomName("Romeoadmin");
		player.setCustomNameVisible(false);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.CLOUD, 4);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.PLAYER;
	}

}
