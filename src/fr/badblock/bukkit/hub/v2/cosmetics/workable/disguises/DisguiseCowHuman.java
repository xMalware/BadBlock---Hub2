package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseCowHuman extends CustomDisguise
{
	/**
	 * For Staff
	 * Deguisement qui represente la vache humaine qui est folle ! (Humour)
	 */
	
	BadblockPlayer player;
	public DisguiseCowHuman(BadblockPlayer player)
	{
		super(player, EntityType.PLAYER);
		player.setCustomName("Cirauk_");
		player.setCustomNameVisible(false);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return new CustomDisguiseEffect(ParticleEffectType.LAVA, 4);
	}

}
