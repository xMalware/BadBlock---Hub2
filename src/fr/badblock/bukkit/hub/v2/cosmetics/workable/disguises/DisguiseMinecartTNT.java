package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMinecartTNT extends CustomDisguise
{
	
	public DisguiseMinecartTNT()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.MINECART_TNT;
	}

}
