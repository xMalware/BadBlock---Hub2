package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguise;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseAdminMCSM;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseArrow;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseBat;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseBlaze;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseBoat;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseCaveSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseChicken;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseCow;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseCowHuman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseCreeper;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseDiscoSheep;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseDroppedItem;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEXPBottle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEnderCrystal;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEnderPearl;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEnderman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEndermite;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseExperience;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseEyeEnder;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseFireball;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseFirework;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseGhast;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseGolem;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseGuardian;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseHorse;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseItemFrame;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseLatitchips;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseLeLanN;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMagmaCube;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMicromaniaque;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecart;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecartChest;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecartCommandBlock;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecartFurnace;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecartHopper;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecartMobSpawner;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMinecartTNT;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMiniUs;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseMushRoom;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguisePainting;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguisePig;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguisePigman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSheep;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSilverfish;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSkeleton;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSmallFireball;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSnowMan;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSnowball;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSplashPotion;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSquid;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseSulfique;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseVillager;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseWitch;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseWitherSkull;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseWolf;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseZombie;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguisexMalware;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum DisguiseFeatures implements IFeatureWorker
{

	ADMINMCSM			(new DisguiseAdminMCSM(null)),
	ARROW				(new DisguiseArrow(null)),
	BAT					(new DisguiseBat(null)),
	BLAZE				(new DisguiseBlaze(null)),
	BOAT				(new DisguiseBoat(null)),
	CAVESPIDER			(new DisguiseCaveSpider(null)),
	CHICKEN				(new DisguiseChicken(null)),
	COW					(new DisguiseCow(null)),
	COWHUMAN			(new DisguiseCowHuman(null)),
	CREEPER				(new DisguiseCreeper(null)),
	DISCOSHEEP			(new DisguiseDiscoSheep(null)),
	DROPPEDITEM			(new DisguiseDroppedItem(null)),
	ENDERCRYSTAL		(new DisguiseEnderCrystal(null)),
	ENDERMAN			(new DisguiseEnderman(null)),
	ENDERMITE			(new DisguiseEndermite(null)),
	ENDERPEARL			(new DisguiseEnderPearl(null)),
	EXPBOTTLE			(new DisguiseEXPBottle(null)),
	EXPERIENCE			(new DisguiseExperience(null)),
	EYEENDER			(new DisguiseEyeEnder(null)),
	FIREBALL			(new DisguiseFireball(null)),
	FIREWORK			(new DisguiseFirework(null)),
	GHAST				(new DisguiseGhast(null)),
	GOLEM				(new DisguiseGolem(null)),
	GUARDIAN			(new DisguiseGuardian(null)),
	HORSE				(new DisguiseHorse(null)),
	ITEMFRAME			(new DisguiseItemFrame(null)),
	LATITCHIPS			(new DisguiseLatitchips(null)),
	LELANN				(new DisguiseLeLanN(null)),
	MAGMACUBE			(new DisguiseMagmaCube(null)),
	MICROMANIAQUE		(new DisguiseMicromaniaque(null)),
	MINECART			(new DisguiseMinecart(null)),
	MINECARTCHEST		(new DisguiseMinecartChest(null)),
	MINECARTCOMMANDBLOCK(new DisguiseMinecartCommandBlock(null)),
	MINECARTFURNACE		(new DisguiseMinecartFurnace(null)),
	MINECARTHOPPER		(new DisguiseMinecartHopper(null)),
	MINECARTMOBSPAWNER	(new DisguiseMinecartMobSpawner(null)),
	MINECARTTNT			(new DisguiseMinecartTNT(null)),
	MINIUS				(new DisguiseMiniUs(null)),
	MUSHROOM			(new DisguiseMushRoom(null)),
	PAINTING			(new DisguisePainting(null)),
	PIG					(new DisguisePig(null)),
	PIGMAN				(new DisguisePigman(null)),
	SHEEP				(new DisguiseSheep(null)),
	SILVERFISH			(new DisguiseSilverfish(null)),
	SKELETON			(new DisguiseSkeleton(null)),
	SMALLFIREBALL		(new DisguiseSmallFireball(null)),
	SNOWBALL			(new DisguiseSnowball(null)),
	SNOWMAN				(new DisguiseSnowMan(null)),
	SPIDER				(new DisguiseSpider(null)),
	SPLASHPOTION		(new DisguiseSplashPotion(null)),
	SQUID				(new DisguiseSquid(null)),
	SULFIQUE			(new DisguiseSulfique(null)),
	VILLAGER			(new DisguiseVillager(null)),
	WITCH				(new DisguiseWitch(null)),
	WITHERSKULL			(new DisguiseWitherSkull(null)),
	WOLF				(new DisguiseWolf(null)),
	XMALWARE			(new DisguisexMalware(null)),
	ZOMBIE				(new DisguiseZombie(null));
	
	private CustomDisguise customDisguise;
	
	DisguiseFeatures(CustomDisguise customDisguise)
	{
		setCustomDisguise(customDisguise);
	}
	
	public void setCustomDisguise(CustomDisguise customDisguise)
	{
		this.customDisguise = customDisguise;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		customDisguise.own(player);
		customDisguise.disguise();
	}
	
	public static void work(BadblockPlayer player, Feature feature)
	{
		String name = feature.getName();
		DisguiseFeatures finalFeature = null;
		for (DisguiseFeatures disguiseFeature : values())
		{
			if (disguiseFeature.name().equalsIgnoreCase(name))
			{
				finalFeature = disguiseFeature;
				break;
			}
		}
		if (finalFeature == null)
		{
			return;
		}
		finalFeature.work(player);
	}
	
}
