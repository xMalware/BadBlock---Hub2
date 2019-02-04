package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.utils.ItemBuilder;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;


public class FireGun extends AbstractGadgets {

    public FireGun() {
        super("Pistolet de feu", new ItemBuilder(Material.DIAMOND_SPADE).toItemStack(), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {
    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            Block block = badblockPlayer.getTargetBlock(new HashSet<Material>(Collections.singletonList(Material.AIR)), 50);
            if (block != null) {
                double size = 4;

                Block b = block.getRelative(BlockFace.UP);
                ArrayList<Location> blocks = getCircle(b.getLocation(), size, (int) size * 12);

                badblockPlayer.getLocation().getDirection().normalize().multiply(1);
                badblockPlayer.launchProjectile(SmallFireball.class);

                if (Material.AIR.equals(b.getType())) {
                    new BukkitRunnable() {
                        int times = 0;
                        int loop = 1;

                        @Override
                        public void run() {
                            loop++;

                            if (loop >= blocks.size()) {
                                if (times >= 3) {
                                    cancel();
                                    return;
                                }

                                times++;
                                loop = 0;
                            }

                            float fl = ((float) loop / (float) Math.max(1, blocks.size())) + (times - 1);
                            Location l = blocks.get(loop).clone().add(0, 2f * fl, 0);

                            for (int i = 0; i < 10; i++) {
                                Random random = new Random();
                                ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(random.nextInt(155) + 100, random.nextInt(50), random.nextInt(50)), l, 32);
                            }
                        }
                    }.runTaskTimer(BadBlockHub.getInstance(), 10L, 1L);

                    return true;
                }

                /*for (int x = -radius; x <= radius; x++)
                {
                    for (int y = -radius; y <= radius; y++)
                    {
                        for (int z = -radius; z <= radius; z++)
                        {
                            Block b = block.getLocation().clone().add(x,y,z).getBlock().getRelative(BlockFace.UP);
                            if (Material.AIR.equals(b.getType()))
                            {
                                BukkitUtils.getAllPlayers().forEach(player -> player.sendBlockChange(b.getLocation(), Material.FIRE, (byte) 0));
                            }
                        }
                    }
                }*/
            }
        }
        return false;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

    }

    @Override
    public int waitingTime() {
        return 3000;
    }

    private ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

}
