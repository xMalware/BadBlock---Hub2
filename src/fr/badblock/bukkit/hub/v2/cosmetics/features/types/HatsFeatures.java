package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.AppleHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.BlazeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CactusHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ChestHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ChickenHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.CreeperHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ExclamationHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.GhastHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.HerobrineHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.MelonHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.MushroomCowHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PigHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.PumpkinHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.QuestionHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SheepHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.SlimeHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.TntHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.VillagerHats;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.type.ZombieHats;
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
	HEROBRINE			(new HerobrineHats()),
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
