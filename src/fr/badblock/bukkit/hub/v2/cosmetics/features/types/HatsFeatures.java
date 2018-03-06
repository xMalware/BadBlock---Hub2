package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureType;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.*;

import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum HatsFeatures implements IFeatureWorker
{

	ANUBIS              (new AnubisHats()),
	APPLE				(new AppleHats()),
	ARTICUNO            (new ArticunoHats()),
	BLAZE				(new BlazeHats()),
	BLUE_GHOST          (new BlueGhostHats()),
	BLUE_ORB            (new BlueOrbHats()),
	BLUE_SHEEP          (new BlueSheepHats()),
	CACTUS				(new CactusHats()),
	CAVESPIDER          (new CaveSpiderHats()),
	CHEST				(new ChestHats()),
	CHICK               (new ChickHats()),
	CHICKEN				(new ChickenHats()),
	CHINESE_DRAGON      (new ChineseDragonHats()),
	CHORUS_PLANT        (new ChorusPlantHats()),
	COLORFUL_CREEPER    (new ColorfulCreeperHats()),
	COMMANDBLOCK        (new CommandBlockHats()),
	COW                 (new CowHats()),
	CRAZY_PROFESSOR     (new CrazyProfessorHats()),
	CREEPER				(new CreeperHats()),
	CRYPTONIT           (new CryptonitHats()),
	CUSTOM_HELMET       (new HelmetHats()),
	DEADPOOL            (new DeadpoolHats()),
	DEMON               (new DemonHats()),
	DIAMOND_ORE         (new DiamondOreHats()),
	DISPENSER           (new DispenserHats()),
	DREADLORD           (new DreadlordHats()),
	EMERALD_BLOCK       (new EmeraldBlockHats()),
	EMERALD_STEVE       (new EmeraldSteveHats()),
	EMPOLEON            (new EmpoleonHats()),
	ENDERCRYSTAL        (new EnderCrystalHats()),
	ENDERLORD           (new EnderlordHats()),
	ENDERMAN            (new EndermanHats()),
	ENDERPEARL          (new EnderpearlHats()),
	ENDERZOMBIE         (new EnderZombieHats()),
	ESPEON              (new EspeonHats()),
	EXCLAMATION			(new ExclamationHats()),
	EVIL_EYE            (new EvilEyeHats()),
	FANCY_CUBE          (new FancyCubeHats()),
	FISH                (new FishHats()),
	FIRE_CREEPER        (new FireCreeperHats()),
	GHAST				(new GhastHats()),
	GHOST               (new GhostHats()),
	GLACEON             (new GlaceonHats()),
	GOLEM               (new GolemHats()),
	GOLD_COW            (new GoldenCowHats()),
	GOLD_STEVE          (new GoldenSteveHats()),
	GREEN_COCONUT       (new GreenCoconutHats()),
	GRIM_REAPER         (new GrimReaperHats()),
	IRONMAN             (new IronManHats()),
	HEROBRINE			(new HerobrineHats()),
	LION                (new LionHats()),
	LAVA                (new LavaHats()),
	LAVASLIME			(new LavaSlimeHats()),
	MELON				(new MelonHats()),
	MUSHROOM			(new MushroomCowHats()),
	OCELOT              (new OcelotHats()),
	ORANGE_SHEEP        (new OrangeSheepHats()),
	ORANGE_SQUID        (new OrangeSquidHats()),
	PENGUIN             (new PenguinHats()),
	PIG					(new PigHats()),
	PIKACHU             (new PikachuHats()),
	PINK_POTION         (new PinkPotionHats()),
	PINK_SHEEP          (new PinkSheepHats()),
	POKEBALL            (new PokeBallHats()),
	PUMPKIN				(new PumpkinHats()),
	PURPLE_ORB          (new PurpleOrbHats()),
	QUESTION			(new QuestionHats()),
	RAINBOW_SLIME       (new RainbowSlimeHats()),
	RED_SQUID           (new RedSquidHats()),
	RUBIX_CUBE          (new RubiksCubeHats()),
	SAMURAI             (new SamuraiHats()),
	SHARK               (new SharkHats()),
	SHEEP				(new SheepHats()),
	SHREK               (new ShrekHats()),
	SLIME				(new SlimeHats()),
	SOLDIER             (new SoldierHats()),
	SPIDER              (new SpiderHats()),
	TNT					(new TntHats()),
	TOXICWASTE          (new ToxicWasteHats()),
	VAPOREON            (new VaporeonHats()),
	VENOM               (new VenomHats()),
	VILLAGER			(new VillagerHats()),
	WERE_WOLF           (new WereWolfHats()),
	WITHERSKELETON      (new WitherSkeletonHats()),
	WOLVERINE           (new WolverineHats()),
	YELLOW_SLIME        (new YellowSlimeHats()),
	ZOMBIE				(new ZombieHats());
	
	private CustomHats customHats;
	
	HatsFeatures(CustomHats customHats)
	{
		setCustomHats(customHats);
	}
	
	public void setCustomHats(CustomHats customHats)
	{
		this.customHats = customHats;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		getCustomHats().deploy(player);
	}
	
	public static void work(BadblockPlayer player, Feature feature)
	{
		String name = feature.getName();
		HatsFeatures finalFeature = null;
		for (HatsFeatures hatsFeature : values())
		{
			if (hatsFeature.name().equalsIgnoreCase(name))
			{
				finalFeature = hatsFeature;
				break;
			}
		}
		if (finalFeature == null)
		{
			return;
		}
		finalFeature.work(player);
	}
	
	public static void generateAll()
	{
		for (HatsFeatures feature : values())
		{
			String rawName = FeatureType.HATS.name().toLowerCase() + "_" + feature.name().toLowerCase();
			FeatureManager.getInstance().generate(rawName);
		}
	}
	
}
