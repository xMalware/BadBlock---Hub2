package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguisePainting extends CustomDisguise
{
	
	public DisguisePainting(BadblockPlayer player)
	{
		super(player, EntityType.PAINTING);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
