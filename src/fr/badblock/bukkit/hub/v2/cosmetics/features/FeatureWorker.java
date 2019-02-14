package fr.badblock.bukkit.hub.v2.cosmetics.features;

import fr.badblock.bukkit.hub.v2.cosmetics.features.types.DisguiseFeatures;
import fr.badblock.bukkit.hub.v2.cosmetics.features.types.GadgetFeatures;
import fr.badblock.bukkit.hub.v2.cosmetics.features.types.MountFeatures;
import fr.badblock.bukkit.hub.v2.cosmetics.features.types.ParticleFeatures;
import fr.badblock.bukkit.hub.v2.cosmetics.features.types.PetFeatures;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.gameapi.players.BadblockPlayer;

public class FeatureWorker {

    public static void work(BadblockPlayer player, Feature feature, String featureRawName) {
        System.out.println(feature.getName() + " > " + feature.getType());
        switch (feature.getType()) {
            case DISGUISE:
                DisguiseFeatures.work(player, featureRawName);
                break;
            case MOUNT:
                MountFeatures.work(player, featureRawName);
                break;
            case PET:
                PetFeatures.work(player, featureRawName);
                break;
            case PARTICLE:
                ParticleFeatures.work(player, featureRawName);
                break;
            case HATS:
                CustomHats.work(player, feature);
                break;
            case GADGET:
                GadgetFeatures.work(player, featureRawName);
                break;
            default:
                break;
        }
    }

}
