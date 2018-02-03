package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.IMount;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountBat;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountBlaze;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountCaveSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountChicken;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountCow;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountCreeper;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountDiscoSheep;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountEnderman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountEndermanSwitched;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountEndermite;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountHorse;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountIronGolem;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountMagmaCube;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountMooshroom;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountOcelot;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountPig;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountRabbit;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountSheepSwitched;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountSkeleton;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountSlime;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountSnowMan;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountSquid;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountVillager;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountWitch;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountWither;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountWolf;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountZombie;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountZombiePigman;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum MountFeatures implements IFeatureWorker
{

	BAT					(new MountBat(null)),
	BLAZE				(new MountBlaze(null)),
	CAVESPIDER			(new MountCaveSpider(null)),
	CHICKEN				(new MountChicken(null)),
	COW					(new MountCow(null)),
	CREEPER				(new MountCreeper(null)),
	DISCOSHEEP			(new MountDiscoSheep(null)),
	ENDERMAN			(new MountEnderman(null)),
	ENDERMANSWITCHED	(new MountEndermanSwitched(null)),
	ENDERMITE			(new MountEndermite(null)),
	HORSE				(new MountHorse(null)),
	IRONGOLEM			(new MountIronGolem(null)),
	MAGMACUBE			(new MountMagmaCube(null)),
	MOOSHROOM			(new MountMooshroom(null)),
	OCELOT				(new MountOcelot(null)),
	PIG					(new MountPig(null)),
	RABBIT				(new MountRabbit(null)),
	SHEEPSWITCHED		(new MountSheepSwitched(null)),
	SKELETON			(new MountSkeleton(null)),
	SLIME				(new MountSlime(null)),
	SNOWMAN				(new MountSnowMan(null)),
	SPIDER				(new MountSpider(null)),
	SQUID				(new MountSquid(null)),
	VILLAGER			(new MountVillager(null)),
	WITCH				(new MountWitch(null)),
	WITHER				(new MountWither(null)),
	WOLF				(new MountWolf(null)),
	ZOMBIE				(new MountZombie(null)),
	ZOMBIEPIGMAN		(new MountZombiePigman(null));
	
	private IMount mount;
	
	MountFeatures(IMount mount)
	{
		setMount(mount);
	}
	
	public void setMount(IMount mount)
	{
		this.mount = mount;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		mount.setPlayer(player);
		mount.spawnEntity(player.getLocation());
	}
	
	public static void work(BadblockPlayer player, Feature feature)
	{
		String name = feature.getName();
		MountFeatures finalFeature = null;
		for (MountFeatures mountFeature : values())
		{
			if (mountFeature.name().equalsIgnoreCase(name))
			{
				finalFeature = mountFeature;
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
