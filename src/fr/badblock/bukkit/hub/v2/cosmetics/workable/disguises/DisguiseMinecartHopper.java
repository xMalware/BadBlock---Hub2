package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMinecartHopper extends CustomDisguise
{
	
	public DisguiseMinecartHopper(BadblockPlayer player)
	{
		super(player, EntityType.MINECART_HOPPER);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
