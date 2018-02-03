package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseVillager extends CustomDisguise
{
	
	public DisguiseVillager(BadblockPlayer player)
	{
		super(player);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SMOKE_NORMAL, 1);
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.VILLAGER;
	}

}
