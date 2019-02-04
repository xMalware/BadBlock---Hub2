package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ChickenGun extends AbstractGadgets{

    public ChickenGun() {
        super("Lanceur de Poulet", new ItemStack(Material.GOLD_HOE), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }


    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {

        Chicken chicken = (Chicken) badblockPlayer.getWorld().spawnEntity(badblockPlayer.getEyeLocation(), EntityType.CHICKEN);
        chicken.setVelocity(badblockPlayer.getEyeLocation().getDirection().multiply(3));
        badblockPlayer.playSound(Sound.CHICKEN_EGG_POP);

        new BukkitRunnable(){
            @Override
            public void run() {
                chicken.getWorld().playEffect(chicken.getLocation(), Effect.EXPLOSION, 50);
                chicken.remove();
            }
        }.runTaskLater(BadBlockHub.getInstance(), 100);

        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

    }

    @Override
    public int waitingTime() {
        return 200;
    }
}
