package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.*;

import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum HatsFeatures implements IFeatureWorker
{

	APPLE				(new AppleHats()),
	BLAZE				(new BlazeHats()),
	CACTUS				(new CactusHats()),
	CAVESPIDER          (new CaveSpiderHats()),
	CHEST				(new ChestHats()),
	CHICKEN				(new ChickenHats()),
	COW                 (new CowHats()),
	CREEPER				(new CreeperHats()),
	ENDERMAN            (new EndermanHats()),
	EXCLAMATION			(new ExclamationHats()),
	GHAST				(new GhastHats()),
	GOLEM               (new GolemHats()),
	HEROBRINE			(new HerobrineHats()),
	LAVASLIME			(new LavaSlimeHats()),
	MELON				(new MelonHats()),
	MUSHROOM			(new MushroomCowHats()),
	OCELOT              (new OcelotHats()),
	PIG					(new PigHats()),
	PUMPKIN				(new PumpkinHats()),
	QUESTION			(new QuestionHats()),
	SHEEP				(new SheepHats()),
	SLIME				(new SlimeHats()),
	SPIDER              (new SpiderHats()),
	TNT					(new TntHats()),
	WITHERSKELETON      (new WitherSkeletonHats()),
	VILLAGER			(new VillagerHats()),
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
	
}
