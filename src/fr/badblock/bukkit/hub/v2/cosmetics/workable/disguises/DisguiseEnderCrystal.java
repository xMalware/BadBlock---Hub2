package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEnderCrystal extends CustomDisguise
{
	
	public DisguiseEnderCrystal()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.PORTAL, 5);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.ENDER_CRYSTAL;
	}

}
