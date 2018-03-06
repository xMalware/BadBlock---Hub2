package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMinecartChest extends CustomDisguise
{
	
	public DisguiseMinecartChest(BadblockPlayer player)
	{
		super(player);
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
