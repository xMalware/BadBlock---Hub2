package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

public class DisguiseMinecartCommandBlock extends CustomDisguise
{
	
	public DisguiseMinecartCommandBlock()
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
		return EntityType.MINECART_COMMAND;
	}

}
