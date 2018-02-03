package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseDiscoSheep extends CustomDisguise
{
	Sheep sheep;
	public DisguiseDiscoSheep(BadblockPlayer player)
	{
		super(player, EntityType.SHEEP);
		sheep.setCustomName("jeb_");
		sheep.setCustomNameVisible(false);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
