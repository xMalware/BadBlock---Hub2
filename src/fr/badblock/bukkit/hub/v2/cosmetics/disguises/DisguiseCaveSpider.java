package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseCaveSpider extends CustomDisguise
{
	
	public DisguiseCaveSpider(BadblockPlayer player)
	{
		super(player, EntityType.CAVE_SPIDER);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return null;
	}
	
}
