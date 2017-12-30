package fr.badblock.bukkit.hub.v2.mounts;

import fr.badblock.bukkit.hub.v2.utils.NMSUtils;
import fr.badblock.gameapi.BadblockPlugin;
import net.minecraft.server.v1_8_R3.*;

public class MountLoader {
	
	
	// Load entities
	public static void load(BadblockPlugin plugin) {
		NMSUtils.registerEntity("MountSlime", 55, EntitySlime.class, MountSlime.class);
		NMSUtils.registerEntity("MountMagmaCube", 62, EntityMagmaCube.class, MountMagmaCube.class);
		NMSUtils.registerEntity("MountPig", 90, EntityPig.class, MountPig.class);
		NMSUtils.registerEntity("MountBlaze", 61, EntityBlaze.class, MountBlaze.class);
		NMSUtils.registerEntity("MountIronGolem", 99, EntityIronGolem.class, MountIronGolem.class);
		NMSUtils.registerEntity("MountSpider", 52, EntitySpider.class, MountSpider.class);
		NMSUtils.registerEntity("MountCaveSpider", 59, EntityCaveSpider.class, MountCaveSpider.class);
		NMSUtils.registerEntity("MountWitch", 66, EntityWitch.class, MountWitch.class);
		NMSUtils.registerEntity("MountCreeper", 50, EntityCreeper.class, MountCreeper.class);
		NMSUtils.registerEntity("MountSkeleton", 51, EntitySkeleton.class, MountSkeleton.class);
		NMSUtils.registerEntity("MountZombie", 54, EntityZombie.class, MountZombie.class);
		NMSUtils.registerEntity("MountWolf", 95, EntityWolf.class, MountWolf.class);
		NMSUtils.registerEntity("MountBat", 65, EntityBat.class, MountBat.class);
		NMSUtils.registerEntity("MountZombiePigman", 57, EntityPigZombie.class, MountZombiePigman.class);
		NMSUtils.registerEntity("MountVillager", 120, EntityVillager.class, MountVillager.class);
		NMSUtils.registerEntity("MountEndermite", 67, EntityEndermite.class, MountEndermite.class);
	}

	
}
