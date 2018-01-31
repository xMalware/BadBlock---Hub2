package fr.badblock.bukkit.hub.v2.pets;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.minecraft.server.v1_8_R3.EntityInsentient;

public class PetsFollowSystem {
	
	public static ArrayList<PetsFollowSystem> task = new ArrayList<>();

	/**
	 * Deploy a Pet and following the BadBlockPlayer
	 * @param player
	 *       Player who Receiving
	 * @param entity
	 *       Entity Pet
	 * @param d
	 *       Speed of the Pet
	 */
	
	public void followPlayer(BadblockPlayer player, LivingEntity entity, double d) {
        final LivingEntity e = entity;
        final BadblockPlayer p = player;
        final float f = (float) d;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BadBlockHub.getInstance(), new Runnable() {
            @Override
            public void run() {
                ((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), f);
            }
        }, 0 * 20, 2 * 20);
    }

}
