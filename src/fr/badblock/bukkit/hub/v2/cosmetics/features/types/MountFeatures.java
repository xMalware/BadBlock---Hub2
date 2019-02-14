package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import org.bukkit.entity.EntityType;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum MountFeatures implements IFeatureWorker {

	BAT(EntityType.BAT),
	BLAZE(EntityType.BLAZE),
	CAVESPIDER(EntityType.CAVE_SPIDER),
	CHICKEN(EntityType.CHICKEN),
	COW(EntityType.COW),
	CREEPER(EntityType.CREEPER),
	DISCOSHEEP(EntityType.SHEEP),
	ENDERMAN(EntityType.ENDERMAN),
	ENDERMITE(EntityType.ENDERMITE),
	HORSE(EntityType.HORSE),
	IRONGOLEM(EntityType.IRON_GOLEM),
	MAGMACUBE(EntityType.MAGMA_CUBE),
	MOOSHROOM(EntityType.COW),
	OCELOT(EntityType.OCELOT),
	PIG(EntityType.PIG),
	RABBIT(EntityType.RABBIT),
	SKELETON(EntityType.SKELETON),
	SLIME(EntityType.SLIME),
	SNOWMAN(EntityType.SNOWMAN),
	SPIDER(EntityType.SPIDER),
	SQUID(EntityType.SQUID),
	VILLAGER(EntityType.VILLAGER),
	WITCH(EntityType.WITCH),
	WITHER(EntityType.WITHER),
	WOLF(EntityType.WOLF),
	ZOMBIE(EntityType.ZOMBIE),
	ZOMBIEPIGMAN(EntityType.PIG_ZOMBIE);

	private EntityType type;

	MountFeatures(EntityType type) {
		this.type = type;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		MountManager.rideEntity(player, type, false, false, 0.3D, false, true);
	}

	public static void work(BadblockPlayer player, String featureName) {
		String[] parser = featureName.split("_");

		if (parser.length < 2) {
			return;
		}

		String name = parser[1];

		Feature feature = ConfigLoader.getFeatures().getFeatures().get(featureName);

		if (feature == null)
		{
			return;
		}

		MountFeatures finalFeature = null;
		for (MountFeatures mountFeature : values()) {
			if (mountFeature.name().equalsIgnoreCase(name)) {
				finalFeature = mountFeature;
				break;
			}
		}

		if (finalFeature == null) {
			return;
		}

		finalFeature.work(player);
	}

}
