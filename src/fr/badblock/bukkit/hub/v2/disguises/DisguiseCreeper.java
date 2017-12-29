package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseCreeper extends CustomDisguise
{
	
	public DisguiseCreeper(BadblockPlayer player)
	{
		super(player, EntityType.CREEPER);
	}

}
