package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseWitch extends CustomDisguise
{
	
	public DisguiseWitch(BadblockPlayer player)
	{
		super(player, EntityType.WITCH);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.SPELL_WITCH, 4);
	}

}
