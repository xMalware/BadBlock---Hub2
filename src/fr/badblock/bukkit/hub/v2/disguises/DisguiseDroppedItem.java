package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseDroppedItem extends CustomDisguise
{
	
	public DisguiseDroppedItem(BadblockPlayer player)
	{
		super(player, EntityType.DROPPED_ITEM);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.FLAME, 1);
	}

}
