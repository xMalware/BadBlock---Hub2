package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

public class DisguiseSkeleton extends CustomDisguise
{
	
	public DisguiseSkeleton()
	{
		super();
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.SKELETON;
	}

}
