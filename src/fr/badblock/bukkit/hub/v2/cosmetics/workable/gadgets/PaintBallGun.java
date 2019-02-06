package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class PaintBallGun extends AbstractGadgets {

    private Map<Location, String> blocks = new HashMap<>();
    private ArrayList<Material> blacklist = new ArrayList<>(Arrays.asList(Material.SIGN, Material.SIGN_POST, Material.WALL_SIGN, Material.DOUBLE_PLANT));

    public PaintBallGun() {
        super("Pistolet Lazer", new ItemStack(Material.DIAMOND_HOE), 4);
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

                if (blocks.containsKey(block.getLocation()) || blacklist.contains(block.getType()))
                    return false;

                badblockPlayer.playSound(Sound.CHICKEN_EGG_POP);
                Location start = badblockPlayer.getEyeLocation().clone();
                Location end = block.getLocation();

                Vector vector = end.toVector().subtract(start.toVector());

                for (double i = 1; i <= start.distance(end); i += 0.5) {
                    vector.multiply(i);
                    start.add(vector);
                    ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(0, 255, 0), start, 32);
                    start.subtract(vector);
                    vector.normalize();
                }

                Random random = new Random();
                int radius = random.nextInt(2) + 1;
                System.out.println(radius);

                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block b = block.getLocation().clone().add(x, y, z).getBlock();
                            if(!(b.getType() == Material.AIR) && !blocks.containsKey(b.getLocation()) && !blacklist.contains(b.getType()))
                                colorBlock(b);
                        }
                    }
                }


            }
        }

        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

    }

    @Override
    public int waitingTime() {
        return 400;
    }

    private void colorBlock(Block block) {
        blocks.put(block.getLocation(), block.getTypeId() + ":" + block.getData());

        block.setType(Material.WOOL);
        DyeColor data = DyeColor.values()[new Random().nextInt(DyeColor.values().length)];
        block.setData(data.getWoolData());

        new BukkitRunnable() {
            @Override
            public void run() {
                String[] newBlock = blocks.remove(block.getLocation()).split(":");
                block.setTypeIdAndData(Integer.parseInt(newBlock[0]), Byte.parseByte(newBlock[1]), true);
            }
        }.runTaskLater(BadBlockHub.getInstance(), 40);
    }
}
