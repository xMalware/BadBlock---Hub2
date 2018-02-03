package fr.badblock.bukkit.hub.v2.cosmetics.pets;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.server.v1_8_R3.EntityInsentient;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Data
public abstract class CustomPet
{

	private Class<? extends LivingEntity> clazz;
	// Follow system
	private boolean						  followable;

	public abstract void onSpawn(LivingEntity livingEntity);
	
	public void spawn(BadblockPlayer player)
	{
		// Spawn part
		LivingEntity entity = player.getWorld().spawn(player.getLocation(), clazz);

		// Follow part
		if (isFollowable())
		{
			followPlayer(player, entity, 1);
		}
		
		// We're calling this event to handle specific cases
		onSpawn(entity);
	}

	public void followPlayer(BadblockPlayer player, LivingEntity entity, double d)
	{
		final LivingEntity e = entity;
		final BadblockPlayer p = player;
		final float f = (float) d;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(BadBlockHub.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), f);
			}
		}, 0 * 20, 2 * 20);
	}

	public void undeploy(BadblockPlayer player)
	{
		// TODO: do something
	}

}
