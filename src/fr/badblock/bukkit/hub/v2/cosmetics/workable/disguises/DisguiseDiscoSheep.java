package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

public class DisguiseDiscoSheep extends CustomDisguise
{
	Sheep sheep;
	public DisguiseDiscoSheep()
	{
		super();
		sheep.setCustomName("jeb_");
		sheep.setCustomNameVisible(false);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.PLAYER;
	}

}
