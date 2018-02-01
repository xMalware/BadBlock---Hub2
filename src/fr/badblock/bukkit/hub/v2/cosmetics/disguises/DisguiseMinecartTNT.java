package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMinecartTNT extends CustomDisguise
{
	
	public DisguiseMinecartTNT(BadblockPlayer player)
	{
		super(player, EntityType.MINECART_TNT);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
