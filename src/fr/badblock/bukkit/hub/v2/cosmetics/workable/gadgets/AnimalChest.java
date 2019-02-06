package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import com.google.common.collect.Sets;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.EntityUtils;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.Random;

public class AnimalChest extends AbstractGadgets {

    private static final EntityType[] TYPES = new EntityType[]{
            EntityType.CAVE_SPIDER, EntityType.COW, EntityType.CREEPER, EntityType.ENDERMAN, EntityType.ENDERMITE,
            EntityType.IRON_GOLEM, EntityType.MAGMA_CUBE, EntityType.OCELOT, EntityType.PIG,
            EntityType.PIG_ZOMBIE, EntityType.RABBIT, EntityType.SHEEP, EntityType.SILVERFISH, EntityType.SKELETON,
            EntityType.SLIME, EntityType.SPIDER, EntityType.SQUID, EntityType.VILLAGER,
            EntityType.WITCH, EntityType.WOLF, EntityType.ZOMBIE, EntityType.HORSE, EntityType.MUSHROOM_COW,
            EntityType.WITHER_SKULL
    };
    private Random random;

    public AnimalChest() {
        super("AnimalChest", new ItemStack(Material.CHEST), 4);

        this.random = new Random();
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        Location centerLoc = badblockPlayer.getLocation();
        centerLoc.setX(centerLoc.getBlockX() + -0.5);
        centerLoc.setX(centerLoc.getBlockX() + -0.75);
        centerLoc.setX(centerLoc.getBlockX() + -0.5);

        BlockPosition position = new BlockPosition(badblockPlayer.getLocation().getX(), badblockPlayer.getLocation().getY(), badblockPlayer.getLocation().getZ());
        Block block = ((CraftWorld) badblockPlayer.getLocation().getWorld()).getHandle().c(position).getBlockData().getBlock();
        PacketPlayOutBlockAction openingpacket = new PacketPlayOutBlockAction(position, block, 1, 1);

        for (Player p : BadBlockHub.getInstance().getServer().getOnlinePlayers())
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(openingpacket);

        BukkitTask spawningTask = Bukkit.getServer().getScheduler().runTaskTimer(BadBlockHub.getInstance(), () -> {
            Entity entity = badblockPlayer.getWorld().spawnEntity(badblockPlayer.getLocation().add(0, 3, 0), TYPES[random.nextInt(TYPES.length)]);
            entity.teleport(badblockPlayer.getLocation());

            freezeEntity(entity);

            double x = Math.random() * 2 - 1;
            double y = Math.random();
            double z = Math.random() * 2 - 1;

            entity.setVelocity(new Vector(x, y, z).multiply(0.9));

            Bukkit.getServer().getScheduler().runTaskLater(BadBlockHub.getInstance(), entity::remove, 20);
        }, 10, 2);

        Bukkit.getServer().getScheduler().runTaskLater(BadBlockHub.getInstance(), spawningTask::cancel, 20L * 10);
        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

    }

    @Override
    public int waitingTime() {
        return 20000;
    }

    private void freezeEntity(Entity e) {
        net.minecraft.server.v1_8_R3.Entity entity = ((CraftEntity) e).getHandle();

        if (!(entity instanceof EntityInsentient))
            return;

        EntityInsentient ce = (EntityInsentient) entity;
        Field bField;

        try {
            bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);

            bField.set(ce.goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(ce.targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(ce.goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(ce.targetSelector, new UnsafeList<PathfinderGoalSelector>());

            ((Navigation) ce.getNavigation()).a(true);
        } catch (ReflectiveOperationException ignored) {
        }
    }
}
