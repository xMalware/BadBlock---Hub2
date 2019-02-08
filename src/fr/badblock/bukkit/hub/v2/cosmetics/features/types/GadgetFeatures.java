package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import com.yapzhenyie.GadgetsMenu.cosmetics.gadgets.GadgetType;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.*;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum GadgetFeatures implements IFeatureWorker {

    FIREGUN(new FireGun()),
    PAINTBALLGUN(new PaintBallGun()),
    CHICKENGUN(new ChickenGun()),
    ENDERPEARLGUN(new EnderPearlGun()),
    COLORBOOT(new ColorBoots()),
    CREEPEREXPLOSER(new CreeperExploser()),
    ANIMALCHEST(new AnimalChest()),
    NUKE(new Nuke()),
    ROCKETLAUNCHER(new RocketLauncher()),
    CLONER(new CloneNPC()),
    POOL("Piscine", new ItemStack(Material.WATER_BUCKET), GadgetType.DIVING_BOARD),
    TRAMPOLINE("Trampoling", new ItemStack(Material.HOPPER), GadgetType.TRAMPOLINE),
    SANDCASTLE("Chateau de sable", new ItemStack(Material.SAND), GadgetType.SAND_CASTLE),
    BBQGRILL("Barbecue", new ItemStack(Material.OBSIDIAN), GadgetType.BBQ_GRILL),
    CATAPULT("Lanceur de chat", new ItemStack(Material.EGG), GadgetType.CATAPULT),
    ROCKET("Fusée", new ItemStack(Material.FIREWORK), GadgetType.ROCKET),
    DISCOBALL("Lanceur de musique", new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3), GadgetType.DISCO_BALL),
    DJ("DJ", new ItemStack(Material.NOTE_BLOCK), GadgetType.DJ_BOOTH),
    DIAMONDSHOWER("Douche de diamant", new ItemStack(Material.DIAMOND), GadgetType.DIAMOND_SHOWER),
    GHOSTS("Tête invisible", new ItemStack(Material.SKULL_ITEM, 1, (short) 1), GadgetType.GHOSTS),
    TNTFOUNTAIN("Fontaine de TNT", new ItemStack(Material.TNT), GadgetType.TNT_FOUNTAIN);

    private AbstractGadgets gadgets;

    GadgetFeatures(AbstractGadgets gadgets) {
        this.gadgets = gadgets;
    }

    GadgetFeatures(String name, ItemStack itemStack, GadgetType gadgetType) {
        this.gadgets = new ExternalGadgets(name, itemStack, gadgetType);
    }

    @Override
    public void work(BadblockPlayer player) {
        if (gadgets.getItem() != null && !gadgets.isExternal())
            player.getInventory().setItem(gadgets.getSlot(), gadgets.getItem());

        this.gadgets.equip(player);
    }

    public static void work(BadblockPlayer player, Feature feature) {
        String[] parser = feature.getName().split("_");

        if (parser.length < 2) {
            return;
        }

        String name = parser[1];
        GadgetFeatures finalFeature = null;
        for (GadgetFeatures gadgetFeature : values()) {
            if (gadgetFeature.name().equalsIgnoreCase(name)) {
                finalFeature = gadgetFeature;
                break;
            }
        }

        System.out.println(feature.getName() + " > " + feature.getType() + " > gadget > " + finalFeature);

        if (finalFeature == null) {
            return;
        }

        System.out.println(feature.getName() + " > " + feature.getType() + " > BBB > work");

        HubPlayer hubPlayer = HubPlayer.get(player);

        if (hubPlayer != null) {
            if (hubPlayer.getCurrentWidget() != null){
                player.getInventory().remove(hubPlayer.getCurrentWidget().getItem());
                hubPlayer.getCurrentWidget().unequip(player);
            }
            hubPlayer.setCurrentWidget(finalFeature.gadgets);
        }

        finalFeature.work(player);
    }

}
