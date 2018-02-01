package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMinecartCommandBlock extends CustomDisguise
{
	
	public DisguiseMinecartCommandBlock(BadblockPlayer player)
	{
		super(player, EntityType.MINECART_COMMAND);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
