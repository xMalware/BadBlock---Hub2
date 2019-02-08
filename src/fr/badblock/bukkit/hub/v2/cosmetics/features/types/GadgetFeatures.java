package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import com.yapzhenyie.GadgetsMenu.cosmetics.gadgets.GadgetType;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.*;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public enum GadgetFeatures implements IFeatureWorker {

    FIREGUN(new FireGun("firegun")),
    PAINTBALLGUN(new PaintBallGun("paintballgun")),
    CHICKENGUN(new ChickenGun("chickengun")),
    ENDERPEARLGUN(new EnderPearlGun("enderpearlgun")),
    COLORBOOT(new ColorBoots("colorboot")),
    CREEPEREXPLOSER(new CreeperExploser("creeperexploser")),
    ANIMALCHEST(new AnimalChest("animalchest")),
    NUKE(new Nuke("nuke")),
    ROCKETLAUNCHER(new RocketLauncher("rocketlauncher")),
    CLONER(new CloneNPC("cloner")),
    POOL("pool", new ItemStack(Material.WATER_BUCKET), GadgetType.DIVING_BOARD),
    TRAMPOLINE("trampoline", new ItemStack(Material.HOPPER), GadgetType.TRAMPOLINE),
    SANDCASTLE("sandcastle", new ItemStack(Material.SAND), GadgetType.SAND_CASTLE),
    BBQGRILL("bbqgrill", new ItemStack(Material.OBSIDIAN), GadgetType.BBQ_GRILL),
    CATAPULT("catapult", new ItemStack(Material.EGG), GadgetType.CATAPULT),
    ROCKET("rocket", new ItemStack(Material.FIREWORK), GadgetType.ROCKET),
    DISCOBALL("discoball", new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3), GadgetType.DISCO_BALL),
    DJ("dj", new ItemStack(Material.NOTE_BLOCK), GadgetType.DJ_BOOTH),
    DIAMONDSHOWER("diamondshower", new ItemStack(Material.DIAMOND), GadgetType.DIAMOND_SHOWER),
    GHOSTS("ghosts", new ItemStack(Material.SKULL_ITEM, 1, (short) 1), GadgetType.GHOSTS),
    TNTFOUNTAIN("tntfountain", new ItemStack(Material.TNT), GadgetType.TNT_FOUNTAIN),

    POOPBOMB("poupbomb", new ItemStack(Material.INK_SACK, 1, (short) 3), GadgetType.POOP_BOMB),
    BATLAUNCHER("batlauncher", new ItemStack(Material.IRON_BARDING), GadgetType.BAT_LAUNCHER),
    EXPLODINGSHEEP("explodingsheep", new ItemStack(Material.SHEARS), GadgetType.EXPLODING_SHEEP),
    CREEPERASTRONAUT("creeperastronaut", new ItemStack(Material.MONSTER_EGG, 1, EntityType.CREEPER.getTypeId()), GadgetType.CREEPER_ASTRONAUT),
    GOLDSHOWER("goldshower", new ItemStack(Material.GOLD_INGOT), GadgetType.GOLD_FOUNTAIN),
    PARTYPOPPER("partypopper", new ItemStack(Material.ENDER_CHEST), GadgetType.PARTY_POPPER),
    COOKIEFOUNTAIN("cookiefountain", new ItemStack(Material.COOKIE), GadgetType.KOOKIE_FOUNTAIN);

	private AbstractGadgets gadgets;

    GadgetFeatures(AbstractGadgets gadgets) {
    	this.gadgets = gadgets;
    }

    GadgetFeatures(String internalName, ItemStack itemStack, GadgetType gadgetType) {
        this.gadgets = new ExternalGadgets(internalName, itemStack, gadgetType);
    }

    @Override
    public void work(BadblockPlayer player) {
        if (gadgets.getItem() != null && !gadgets.isExternal())
            player.getInventory().setItem(gadgets.getSlot(), gadgets.getItem());

        this.gadgets.equip(player);
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
        
        GadgetFeatures finalFeature = null;
        for (GadgetFeatures gadgetFeature : values()) {
            if (gadgetFeature.name().equalsIgnoreCase(name)) {
                finalFeature = gadgetFeature;
                break;
            }
        }

        if (finalFeature == null) {
            return;
        }

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
