package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

public class DisguiseMinecartChest extends CustomDisguise
{
	
	public DisguiseMinecartChest()
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
		return EntityType.MINECART_CHEST;
	}

}
