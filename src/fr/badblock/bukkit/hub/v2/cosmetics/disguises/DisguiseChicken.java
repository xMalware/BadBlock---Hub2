package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseChicken extends CustomDisguise
{
	
	public DisguiseChicken(BadblockPlayer player)
	{
		super(player, EntityType.CHICKEN);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return null;
	}
	
}
