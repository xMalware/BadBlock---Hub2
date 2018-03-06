package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureType;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.AnubisHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.AppleHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ArticunoHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.BlazeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.BlueGhostHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.BlueOrbHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.BlueSheepHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CactusHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CaveSpiderHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ChestHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ChickHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ChickenHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ChineseDragonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ChorusPlantHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ColorfulCreeperHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CommandBlockHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CowHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CrazyProfessorHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CreeperHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CryptonitHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.DeadpoolHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.DemonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.DiamondOreHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.DispenserHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.DreadlordHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EmeraldBlockHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EmeraldSteveHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EmpoleonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EnderCrystalHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EnderZombieHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EnderlordHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EndermanHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EnderpearlHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EspeonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.EvilEyeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ExclamationHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.FancyCubeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.FireCreeperHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.FishHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GhastHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GhostHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GlaceonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GoldenCowHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GoldenSteveHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GolemHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GreenCoconutHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GrimReaperHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.HelmetHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.HerobrineHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.IronManHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.LavaHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.LavaSlimeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.LionHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.MelonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.MushroomCowHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.OcelotHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.OrangeSheepHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.OrangeSquidHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PenguinHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PigHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PikachuHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PinkPotionHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PinkSheepHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PokeBallHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PumpkinHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PurpleOrbHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.QuestionHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.RainbowSlimeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.RedSquidHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.RubiksCubeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SamuraiHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SharkHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SheepHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ShrekHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SlimeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SoldierHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SpiderHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.TntHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ToxicWasteHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.VaporeonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.VenomHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.VillagerHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.WereWolfHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.WitherSkeletonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.WolverineHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.YellowSlimeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ZombieHats;
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
		String[] splitter = feature.getName().split("_");
		if (splitter.length != 2)
		{
			return;
		}
		HatsFeatures finalFeature = null;
		for (HatsFeatures hatsFeature : values())
		{
			if (hatsFeature.name().equalsIgnoreCase(splitter[1]))
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
