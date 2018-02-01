package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseSilverfish extends CustomDisguise
{
	
	public DisguiseSilverfish(BadblockPlayer player)
	{
		super(player, EntityType.SILVERFISH);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
