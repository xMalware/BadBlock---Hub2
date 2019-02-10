package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;

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
