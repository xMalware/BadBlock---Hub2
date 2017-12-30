package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMushRoom extends CustomDisguise
{
	
	public DisguiseMushRoom(BadblockPlayer player)
	{
		super(player, EntityType.MUSHROOM_COW);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.VILLAGER_ANGRY, 3);
	}

}
