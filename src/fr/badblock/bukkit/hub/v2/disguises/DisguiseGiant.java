package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseGiant extends CustomDisguise
{
	
	public DisguiseGiant(BadblockPlayer player)
	{
		super(player, EntityType.GIANT);
	}

}
