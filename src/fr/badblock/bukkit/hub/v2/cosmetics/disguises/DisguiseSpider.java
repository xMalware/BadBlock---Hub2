package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseSpider extends CustomDisguise
{
	
	public DisguiseSpider(BadblockPlayer player)
	{
		super(player, EntityType.SPIDER);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
