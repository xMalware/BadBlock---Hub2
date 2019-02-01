package fr.badblock.bukkit.hub.v2.utils;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.fakeentities.FakeEntity;
import fr.badblock.gameapi.packets.watchers.WatcherEntity;

public class EntityUtils
{

	public static EntityType getEntityType(String rawName)
	{
		for (EntityType entityType : EntityType.values())
		{
			if (entityType.name().equalsIgnoreCase(rawName))
			{
				return entityType;
			}
		}
		
		return null;
	}
	
	public static <T extends WatcherEntity> FakeEntity<T> spawn(Location location, EntityType type, Class<T> clazz, boolean movable, boolean rideable, boolean canFly, boolean inversed, String customName)
	{
		FakeEntity<T> fakeEntity = GameAPI.getAPI().spawnFakeLivingEntity(location, type, clazz);

		if (customName != null)
		{
			fakeEntity.getWatchers().setCustomName(customName);
			fakeEntity.getWatchers().setCustomNameVisible(true);
		}
		else if (inversed)
		{
			fakeEntity.getWatchers().setCustomName("Dinnerbone");
			fakeEntity.getWatchers().setCustomNameVisible(false);
		}

		if (fakeEntity == null)
		{
			return null;
		}

		fakeEntity.getWatchers().setOnFire(false);
		return fakeEntity;
	}

}