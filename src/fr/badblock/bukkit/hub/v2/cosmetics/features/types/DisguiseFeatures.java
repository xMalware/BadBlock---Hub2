package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import org.bukkit.Bukkit;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguise;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseBat;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseBlaze;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseBoat;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseCaveSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseChicken;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseCow;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseCreeper;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEnderCrystal;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEnderman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEndermite;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseGhast;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseGolem;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseGuardian;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseHorse;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMagmaCube;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecart;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMushRoom;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguisePig;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguisePigman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSheep;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSilverfish;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSkeleton;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSnowMan;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSquid;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseVillager;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseWitch;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseWitherSkull;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseWolf;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseZombie;
import fr.badblock.bukkit.hub.v2.utils.DisguiseUtil;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum DisguiseFeatures implements IFeatureWorker {

    BAT(new DisguiseBat()),
    BLAZE(new DisguiseBlaze()),
    BOAT(new DisguiseBoat()),
    CAVESPIDER(new DisguiseCaveSpider()),
    CHICKEN(new DisguiseChicken()),
    COW(new DisguiseCow()),
    CREEPER(new DisguiseCreeper()),
    ENDERCRYSTAL(new DisguiseEnderCrystal()),
    ENDERMAN(new DisguiseEnderman()),
    ENDERMITE(new DisguiseEndermite()),
    //ENDERPEARL(new DisguiseEnderPearl()),
    //EXPBOTTLE(new DisguiseEXPBottle()),
    //EXPERIENCE(new DisguiseExperience()),
    //EYEENDER(new DisguiseEyeEnder()),
    //FIREBALL(new DisguiseFireball()),
    //FIREWORK(new DisguiseFirework()),
    GHAST(new DisguiseGhast()),
    GOLEM(new DisguiseGolem()),
    GUARDIAN(new DisguiseGuardian()),
    HORSE(new DisguiseHorse()),
    //ITEMFRAME(new DisguiseItemFrame()),
    MAGMACUBE(new DisguiseMagmaCube()),
    MINECART(new DisguiseMinecart()),
    //MINECARTCHEST(new DisguiseMinecartChest()),
    //MINECARTCOMMANDBLOCK(new DisguiseMinecartCommandBlock()),
    //MINECARTFURNACE(new DisguiseMinecartFurnace()),
    //MINECARTHOPPER(new DisguiseMinecartHopper()),
    //MINECARTMOBSPAWNER(new DisguiseMinecartMobSpawner()),
    //MINECARTTNT(new DisguiseMinecartTNT()),
    MUSHROOM(new DisguiseMushRoom()),
    //PAINTING(new DisguisePainting()),
    PIG(new DisguisePig()),
    PIGMAN(new DisguisePigman()),
    SHEEP(new DisguiseSheep()),
    SILVERFISH(new DisguiseSilverfish()),
    SKELETON(new DisguiseSkeleton()),
    //SMALLFIREBALL(new DisguiseSmallFireball()),
    //SNOWBALL(new DisguiseSnowball()),
    SNOWMAN(new DisguiseSnowMan()),
    SPIDER(new DisguiseSpider()),
    //SPLASHPOTION(new DisguiseSplashPotion()),
    SQUID(new DisguiseSquid()),
    VILLAGER(new DisguiseVillager()),
    WITCH(new DisguiseWitch()),
    WITHERSKULL(new DisguiseWitherSkull()),
    WOLF(new DisguiseWolf()),
    ZOMBIE(new DisguiseZombie());

    private CustomDisguise customDisguise;

    DisguiseFeatures(CustomDisguise customDisguise) {
        this.customDisguise = customDisguise;
    }

    @Override
    public void work(BadblockPlayer player) {
        DisguiseUtil disguiseUtil = new DisguiseUtil(customDisguise.getEntityType(), player);
        disguiseUtil.disguisePlayer(Bukkit.getOnlinePlayers());
        disguiseUtil.setCustomName(player.getName());
    }

    public static void work(BadblockPlayer player, String featureName) {
        String[] parser = featureName.split("_");

        if (parser.length < 2) {
            return;
        }

        String name = parser[1];

        Feature feature = ConfigLoader.getFeatures().getFeatures().get(featureName);

        System.out.println("Feature " + featureName + " > AAAA");

        if (feature == null) {
            return;
        }

        System.out.println("Feature " + featureName + " > BBBBBB");

        DisguiseFeatures finalFeature = null;
        for (DisguiseFeatures disguiseFeature : values()) {
            if (disguiseFeature.name().equalsIgnoreCase(name)) {
                finalFeature = disguiseFeature;
                break;
            }
        }
        if (finalFeature == null) {
            return;
        }

        System.out.println("Feature " + featureName + " > CCCCC");

        finalFeature.work(player);
    }

    public static void generateAll() {
		/*for (DisguiseFeatures feature : values())
		{
			String rawName = FeatureType.DISGUISE.name().toLowerCase() + "_" + feature.name().toLowerCase();
			FeatureManager.getInstance().generate(rawName);
		}*/
    }

}
