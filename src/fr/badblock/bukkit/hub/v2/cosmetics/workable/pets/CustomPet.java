package fr.badblock.bukkit.hub.v2.cosmetics.workable.pets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.PathEntity;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Data
public abstract class CustomPet
{

	private Class<? extends LivingEntity> clazz;
	// Follow system
	private boolean						  followable;

	public abstract String getSoundSystem();

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
		new BukkitRunnable(){
			@SuppressWarnings("deprecation")
			public void run(){
				if ((!entity.isValid() || (!player.isOnline())))
				{
					entity.remove();
					this.cancel();
				}
				net.minecraft.server.v1_8_R3.Entity pett = ((CraftEntity) entity).getHandle();
				((EntityInsentient) pett).getNavigation().a(2);
				Object petf = ((CraftEntity) entity).getHandle();
				Location targetLocation = player.getLocation();
				PathEntity path;
				path = ((EntityInsentient) petf).getNavigation().a(targetLocation.getX() + 1, targetLocation.getY(), targetLocation.getZ() + 1);
				if (path != null) {
					((EntityInsentient) petf).getNavigation().a(path, 1.0D);
					((EntityInsentient) petf).getNavigation().a(2.0D);}
				if (!Bukkit.getPlayer(player.getName()).getLocation().getWorld().equals(entity.getWorld()))
				{
					entity.teleport(player.getLocation());
				}
				else
				{
					int distance = (int) Bukkit.getPlayer(player.getName()).getLocation().distance(entity.getLocation());
					if (distance > 10 && !entity.isDead() && player.isOnGround()) {
						entity.teleport(player.getLocation());}
				}
				AttributeInstance attributes = ((EntityInsentient)((CraftEntity)entity).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
				attributes.setValue(0.3);
			}}.runTaskTimer(BadBlockHub.getInstance(), 0L, 1L);
	}

	public void undeploy(BadblockPlayer player)
	{
		// TODO: do something
	}

}
