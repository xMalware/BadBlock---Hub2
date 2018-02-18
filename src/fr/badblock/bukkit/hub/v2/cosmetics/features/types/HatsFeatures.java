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
	CHEST				(new ChestHats()),
	CHICKEN				(new ChickenHats()),
	CREEPER				(new CreeperHats()),
	EXCLAMATION			(new ExclamationHats()),
	GHAST				(new GhastHats()),
	GOLEM               (new GolemHats()),
	HEROBRINE			(new HerobrineHats()),
	LAVASLIME			(new LavaSlimeHats()),
	MELON				(new MelonHats()),
	MUSHROOM			(new MushroomCowHats()),
	PIG					(new PigHats()),
	PUMPKIN				(new PumpkinHats()),
	QUESTION			(new QuestionHats()),
	SHEEP				(new SheepHats()),
	SLIME				(new SlimeHats()),
	TNT					(new TntHats()),
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
