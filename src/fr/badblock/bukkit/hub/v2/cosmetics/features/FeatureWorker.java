package fr.badblock.bukkit.hub.v2.cosmetics.features;

import fr.badblock.bukkit.hub.v2.cosmetics.features.types.*;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.gameapi.players.BadblockPlayer;

public class FeatureWorker {

    public static void work(BadblockPlayer player, Feature feature, String featureRawName) {
        System.out.println(feature.getName() + " > " + feature.getType());
        switch (feature.getType()) {
            case DISGUISE:
                DisguiseFeatures.work(player, feature);
                break;
            case MOUNT:
                MountFeatures.work(player, feature);
                break;
            case PET:
                PetFeatures.work(player, feature);
                break;
            case PARTICLE:
                ParticleFeatures.work(player, feature);
                break;
            case HATS:
                CustomHats.work(player, feature);
                break;
            case GADGET:
                System.out.println(feature.getName() + " > " + feature.getType() + " > gadget");
                GadgetFeatures.work(player, featureRawName);
                break;
            default:
                break;
        }
    }

}
