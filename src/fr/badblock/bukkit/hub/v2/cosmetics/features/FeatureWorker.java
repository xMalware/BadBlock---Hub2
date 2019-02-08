package fr.badblock.bukkit.hub.v2.cosmetics.features;

import fr.badblock.bukkit.hub.v2.cosmetics.features.types.*;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.gameapi.players.BadblockPlayer;

public class FeatureWorker {

    public static void work(BadblockPlayer player, Feature feature, String featureName) {
        System.out.println(feature.getName() + " > " + feature.getType());
        switch (feature.getType()) {
            case DISGUISE:
                DisguiseFeatures.work(player, feature);
                break;
            case MOUNT:
                MountFeatures.work(player, feature);
                break;
            case PET:
                PetFeatures.work(player, featureName);
                break;
            case PARTICLE:
                ParticleFeatures.work(player, feature);
                break;
            case HATS:
                CustomHats.work(player, feature);
                break;
            case GADGET:
                GadgetFeatures.work(player, featureName);
                break;
            default:
                break;
        }
    }

}
