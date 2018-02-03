package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseSheep extends CustomDisguise
{
	
	public DisguiseSheep(BadblockPlayer player)
	{
		super(player, EntityType.SHEEP);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
