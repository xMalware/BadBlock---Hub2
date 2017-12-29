package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseGuardian extends CustomDisguise
{
	
	public DisguiseGuardian(BadblockPlayer player)
	{
		super(player, EntityType.GUARDIAN);
	}

}
