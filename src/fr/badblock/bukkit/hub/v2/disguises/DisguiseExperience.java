package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseExperience extends CustomDisguise
{
	
	public DisguiseExperience(BadblockPlayer player)
	{
		super(player, EntityType.EXPERIENCE_ORB);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		return null;
	}

}
