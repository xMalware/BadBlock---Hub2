package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.CustomPet;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsBabyOcelot;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsBlaze;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsCaveSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsChicken;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsCow;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsCreeper;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsDiscoSheep;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsEnderman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsEndermite;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsEnderpearl;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsGiant;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsGuardian;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsHorse;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsIronGolem;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsMagmaCube;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsOcelot;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsPig;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsRabbit;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsSheep;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsSilverfish;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsSkeleton;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsSlime;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsSnowman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsSquid;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsVillager;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsWitch;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsWolf;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsXPBottle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsZombie;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.pets.PetsZombiePigman;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum PetFeatures implements IFeatureWorker
{

	BABYOCELOT			(new PetsBabyOcelot()),
	BLAZE				(new PetsBlaze()),
	CAVESPIDER			(new PetsCaveSpider()),
	CHICKEN				(new PetsChicken()),
	COW					(new PetsCow()),
	CREEPER				(new PetsCreeper()),
	DISCOSHEEP			(new PetsDiscoSheep()),
	ENDERMAN			(new PetsEnderman()),
	ENDERMITE			(new PetsEndermite()),
	ENDERPEARL			(new PetsEnderpearl()),
	GIANT				(new PetsGiant()),
	GUARDIAN			(new PetsGuardian()),
	HORSE				(new PetsHorse()),
	IRONGOLEM			(new PetsIronGolem()),
	MAGMACUBE			(new PetsMagmaCube()),
	OCELOT				(new PetsOcelot()),
	PIG					(new PetsPig()),
	RABBIT				(new PetsRabbit()),
	SHEEP				(new PetsSheep()),
	SILVERFISH			(new PetsSilverfish()),
	SKELETON			(new PetsSkeleton()),
	SLIME				(new PetsSlime()),
	SNOWMAN				(new PetsSnowman()),
	SPIDER				(new PetsSpider()),
	SQUID				(new PetsSquid()),
	VILLAGER			(new PetsVillager()),
	WITCH				(new PetsWitch()),
	WOLF				(new PetsWolf()),
	XPBOTTLE			(new PetsXPBottle()),
	ZOMBIE				(new PetsZombie()),
	ZOMBIEPIGMAN		(new PetsZombiePigman());

	private CustomPet customPet;

	PetFeatures(CustomPet customPet)
	{
		setCustomPet(customPet);
	}

	public void setCustomPet(CustomPet customPet)
	{
		this.customPet = customPet;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		getCustomPet().spawn(player);
	}

	public static void work(BadblockPlayer player, Feature feature)
	{
		String name = feature.getName();
		PetFeatures finalFeature = null;
		for (PetFeatures petFeature : values())
		{
			if (petFeature.name().equalsIgnoreCase(name))
			{
				finalFeature = petFeature;
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
