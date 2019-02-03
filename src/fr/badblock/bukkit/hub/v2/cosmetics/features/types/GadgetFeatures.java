package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.AbstractGadgets;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.FireGun;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.PaintBallGun;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public enum GadgetFeatures implements IFeatureWorker {

    FIREGUN (new FireGun()),
    PAINTBALLGUN (new PaintBallGun());

    private AbstractGadgets gadgets;

    GadgetFeatures(AbstractGadgets gadgets) {
        this.gadgets = gadgets;
    }

    @Override
    public void work(BadblockPlayer player)
    {
        System.out.println("CCCCCC");
        player.getInventory().setItem(gadgets.getSlot(), gadgets.getItem());
        this.gadgets.equip(player);
    }

    public static void work(BadblockPlayer player, Feature feature)
    {
        String[] parser = feature.getName().split("_");

        if (parser.length < 2)
        {
            return;
        }

        String name = parser[1];
        GadgetFeatures finalFeature = null;
        for (GadgetFeatures gadgetFeature : values())
        {
            if (gadgetFeature.name().equalsIgnoreCase(name))
            {
                finalFeature = gadgetFeature;
                break;
            }
        }

        System.out.println(feature.getName() + " > " + feature.getType() + " > gadget > " + finalFeature);

        if (finalFeature == null)
        {
            return;
        }

        System.out.println(feature.getName() + " > " + feature.getType() + " > BBB > work");

        HubPlayer hubPlayer = HubPlayer.get(player);

        if (hubPlayer != null)
        {
            hubPlayer.setCurrentWidget(finalFeature.gadgets);
        }

        finalFeature.work(player);
    }

}
