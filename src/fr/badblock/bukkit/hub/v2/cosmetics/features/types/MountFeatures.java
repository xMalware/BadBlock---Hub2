package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountBat;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountBlaze;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountCaveSpider;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountChicken;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountCow;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountCreeper;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountDiscoSheep;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountEnderman;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountEndermite;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountHorse;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountIronGolem;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountMagmaCube;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountMooshroom;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountOcelot;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountPig;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts.MountRabbit;
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
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;

@Getter
public enum MountFeatures implements IFeatureWorker {

	BAT(new MountBat()),
	BLAZE(new MountBlaze()),
	CAVESPIDER(new MountCaveSpider()),
	CHICKEN(new MountChicken()),
	COW(new MountCow()),
	CREEPER(new MountCreeper()),
	DISCOSHEEP(new MountDiscoSheep()),
	ENDERMAN(new MountEnderman()),
	ENDERMITE(new MountEndermite()),
	HORSE(new MountHorse()),
	IRONGOLEM(new MountIronGolem()),
	MAGMACUBE(new MountMagmaCube()),
	MOOSHROOM(new MountMooshroom()),
	OCELOT(new MountOcelot()),
	PIG(new MountPig()),
	RABBIT(new MountRabbit()),
	SKELETON(new MountSkeleton()),
	SLIME(new MountSlime()),
	SNOWMAN(new MountSnowMan()),
	SPIDER(new MountSpider()),
	SQUID(new MountSquid()),
	VILLAGER(new MountVillager()),
	WITCH(new MountWitch()),
	WITHER(new MountWither()),
	WOLF(new MountWolf()),
	ZOMBIE(new MountZombie()),
	ZOMBIEPIGMAN(new MountZombiePigman());

	private Entity mount;

	MountFeatures(Entity mount) {
		this.mount = mount;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		try
		{
			World w = ((CraftWorld) player.getLocation().getWorld()).getHandle();
			Entity entity = mount.getClass().getConstructor(World.class).newInstance(w);

			Location l = player.getLocation();

			entity.spawnIn(w);
			entity.setLocation(l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
			player.setPassenger(entity.getBukkitEntity());
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
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

		MountFeatures finalFeature = null;
		for (MountFeatures mountFeature : values()) {
			if (mountFeature.name().equalsIgnoreCase(name)) {
				finalFeature = mountFeature;
				break;
			}
		}

		if (finalFeature == null) {
			return;
		}

		finalFeature.work(player);
	}

}
