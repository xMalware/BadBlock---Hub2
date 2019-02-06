package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RocketLauncher extends AbstractGadgets {


    public RocketLauncher() {
        super("Fusée", new ItemStack(Material.FIREWORK), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {

        new BukkitRunnable() {

            int radius = 3;
            int i = 3;

            @Override
            public void run() {
                if (i <= 0) {
                    badblockPlayer.sendMessage("§cDécollage..");
                    badblockPlayer.playSound(Sound.FUSE);
                    start(badblockPlayer);
                    cancel();
                    return;
                }

                badblockPlayer.sendMessage("§cDécollage dans.. §3" + i);
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        Location location = badblockPlayer.getLocation().clone().add(x, 0, z);
                        ParticleEffect.FLAME.display(0, 0, 0, 0, 3, location, 64);
                    }
                }
                i--;
            }
        }.runTaskTimer(BadBlockHub.getInstance(), 0, 20);


        return true;
    }

    private void start(BadblockPlayer badblockPlayer) {
        int max = (int) (badblockPlayer.getLocation().getY() + 50);
        Vector vector = new Vector(0, 0.8, 0);

        new BukkitRunnable() {

            int times = 15;

            @Override
            public void run() {
                if (badblockPlayer.getLocation().getY() >= max || times <= 0) {
                    launchFireWork(badblockPlayer.getLocation().add(0, 4, 0));
                    badblockPlayer.setVelocity(new Vector(0, 0.2, 0));

                    cancel();
                    return;
                }

                badblockPlayer.playSound(Sound.BLAZE_BREATH);
                badblockPlayer.setVelocity(vector);
                ParticleEffect.FLAME.display(0, 0, 0, 1, 5, badblockPlayer.getLocation().subtract(0, 1, 0), 64);
                times--;
            }
        }.runTaskTimer(BadBlockHub.getInstance(), 0, 3);

    }

    private void launchFireWork(Location loc) {
        Firework f = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);

        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.STAR)
                .withColor(Color.GREEN).withFade(Color.BLUE).build());
        fm.setPower(1);
        f.setFireworkMeta(fm);

    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

    }

    @Override
    public int waitingTime() {
        return 15000;
    }
}
