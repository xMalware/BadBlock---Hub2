package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEnderCrystal extends CustomDisguise
{
	
	public DisguiseEnderCrystal(BadblockPlayer player)
	{
		super(player, EntityType.ENDER_CRYSTAL);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.PORTAL, 5);
	}

}
