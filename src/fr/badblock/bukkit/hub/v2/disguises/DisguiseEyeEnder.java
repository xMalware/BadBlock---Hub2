package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEyeEnder extends CustomDisguise
{
	
	public DisguiseEyeEnder(BadblockPlayer player)
	{
		super(player, EntityType.ENDER_SIGNAL);
	}

}
