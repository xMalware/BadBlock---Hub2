package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseEXPBottle extends CustomDisguise
{
	
	public DisguiseEXPBottle(BadblockPlayer player)
	{
		super(player, EntityType.THROWN_EXP_BOTTLE);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
