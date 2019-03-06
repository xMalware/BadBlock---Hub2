package fr.badblock.bukkit.hub.v2.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.npc.NPC;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.BukkitUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class FNPC {

	int entityid;
	String texture;
	String signature;
	String name;
	UUID uuid;
	public EntityPlayer npc;
	Location loc;
	public NPC n;
	public static HashMap<Integer, FNPC> npcs = new HashMap<>();

	public FNPC(String texture, String signature, Location loc, NPC npc) {
		this.texture = texture;
		this.signature = signature;
		this.loc = loc;
		this.n = npc;
	}

	public long spawn(String npc) {
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
		this.name = npc;

		System.out.println(loc.toString());
		
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");

		if (texture != null && signature != null && !texture.isEmpty() && !signature.isEmpty())
		{
			gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
		}

		this.npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile,
				new PlayerInteractManager(nmsWorld));

		this.npc.setLocation(this.loc.getX(), this.loc.getY(),
				this.loc.getZ(), this.loc.getYaw(), this.loc.getPitch());
		
		this.npc.yaw = this.loc.getYaw();
		
		this.npc.spawnIn(nmsWorld);
		this.npc.yaw = this.loc.getYaw();
		this.npc.setLocation(this.loc.getX(), this.loc.getY(),
				this.loc.getZ(), this.loc.getYaw(), this.loc.getPitch());

		this.npc.onGround = true;

		this.entityid = this.npc.getId();
		this.npc.ping = 0;

		this.npc.setCustomName("hello");
		this.npc.setCustomNameVisible(false);
		
		for (BadblockPlayer pc : BukkitUtils.getAllPlayers())
		{
			PlayerConnection connection = ((CraftPlayer) pc).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
					new EntityPlayer[] { this.npc }));
			connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY,
					new EntityPlayer[] { this.npc }));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.npc));
			connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.npc, (byte) ((loc.getYaw() * 256.0F) / 360.0F)));

			Bukkit.getScheduler().runTaskLater(BadBlockHub.getInstance(), new Runnable()
			{
				@Override
				public void run()
				{
					connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
							new EntityPlayer[] { FNPC.this.npc }));
				}
			}, 1);
		}

		npcs.put(this.npc.getId(), this);
		return this.getEntityId();
	}

	public void despawn() {
		npcs.remove(this.npc.getId());

		for (BadblockPlayer pc : BukkitUtils.getAllPlayers())
		{
			final PlayerConnection connection = ((CraftPlayer) pc).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
		}

		npc.die();
	}

	public Integer getEntityId() {
		return Integer.valueOf(this.entityid);
	}

	public Location getLocation() {
		return loc;
	}

}
