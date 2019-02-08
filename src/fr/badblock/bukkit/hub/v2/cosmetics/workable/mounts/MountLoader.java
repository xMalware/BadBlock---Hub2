package fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.cosmetics.features.types.PetFeatures;
import fr.badblock.bukkit.hub.v2.utils.NMSUtils;
import fr.badblock.gameapi.BadblockPlugin;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityEndermite;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EntityZombie;

public class MountLoader {


	// Load entities
	public static void load(BadblockPlugin plugin)
	{
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
		NMSUtils.registerEntity("MountSheep", 91, EntitySheep.class, MountSheep.class);
		NMSUtils.registerEntity("MountSheepSwitched", 91, EntitySheep.class, MountSheepSwitched.class);
		NMSUtils.registerEntity("MountDiscoSheep", 91, EntitySheep.class, MountDiscoSheep.class);
		NMSUtils.registerEntity("MountSnowMan", 97, EntitySnowman.class, MountSnowMan.class);
		NMSUtils.registerEntity("MountWither", 64, EntityWither.class, MountWither.class);
		NMSUtils.registerEntity("MountRabbit", 101, EntityRabbit.class, MountRabbit.class);
		NMSUtils.registerEntity("MountHorse", 100, EntityHorse.class, MountHorse.class);
		NMSUtils.registerEntity("MountMooshroom", 96, EntityMushroomCow.class, MountMooshroom.class);
		NMSUtils.registerEntity("MountChicken", 93, EntityChicken.class, MountChicken.class);
		NMSUtils.registerEntity("MountOcelot", 98, EntityOcelot.class, MountOcelot.class);
		NMSUtils.registerEntity("MountEnderman", 58, EntityEnderman.class, MountEnderman.class);
		NMSUtils.registerEntity("MountEndermanSwitched", 58, EntityEnderman.class, MountEndermanSwitched.class);
		NMSUtils.registerEntity("MountCow", 92, EntityCow.class, MountCow.class);

		final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketAdapter(BadBlockHub.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
					@Override
					public void onPacketSending(PacketEvent event) {
						 if (!event.getPacketType().equals(PacketType.Play.Server.NAMED_SOUND_EFFECT)) {
							 return;
						 }

						 String sound = event.getPacket()
						.getStrings()
						.read(0);
						 
						 for (PetFeatures pet : PetFeatures.values())
						 {
							 if (pet.getCustomPet() == null)
							 {
								 continue;
							 }
							 
							 if (sound.toUpperCase().contains(pet.getCustomPet().getSoundSystem().toUpperCase()))
							 {
								 event.setCancelled(true);
								 return;
							 }
						 }
					}
				});
	}


}
