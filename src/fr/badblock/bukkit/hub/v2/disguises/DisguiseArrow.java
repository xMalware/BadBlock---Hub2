package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseArrow extends CustomDisguise
{
	
	public DisguiseArrow(BadblockPlayer player)
	{
		super(player, EntityType.ARROW);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		// TODO Auto-generated method stub
		return null;
	}

}
