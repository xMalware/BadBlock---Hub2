package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseWitherSkull extends CustomDisguise
{
	
	public DisguiseWitherSkull()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.ENCHANTMENT_TABLE, 3);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.WITHER_SKULL;
	}

}
