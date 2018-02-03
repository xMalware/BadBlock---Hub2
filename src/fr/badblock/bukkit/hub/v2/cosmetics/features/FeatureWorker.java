package fr.badblock.bukkit.hub.v2.cosmetics.features;

import fr.badblock.bukkit.hub.v2.cosmetics.features.types.DisguiseFeatures;
import fr.badblock.bukkit.hub.v2.cosmetics.features.types.MountFeatures;
import fr.badblock.gameapi.players.BadblockPlayer;

public class FeatureWorker
{

	public static void work(BadblockPlayer player, Feature feature)
	{
		switch (feature.getType())
		{
		case DISGUISE:
			DisguiseFeatures.work(player, feature);
			break;
		case MOUNT:
			MountFeatures.work(player, feature);
			break;
		default:
			break;
		}
	}

}
